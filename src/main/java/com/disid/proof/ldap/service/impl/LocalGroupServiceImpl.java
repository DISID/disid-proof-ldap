package com.disid.proof.ldap.service.impl;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import com.disid.proof.ldap.config.LdapProperties;
import com.disid.proof.ldap.model.LocalGroup;
import com.disid.proof.ldap.service.api.LocalGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

/**
 * = LocalGroupServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl( service = LocalGroupService.class )
public class LocalGroupServiceImpl implements LocalGroupService
{

  @Autowired
  private LdapTemplate ldapTemplate;

  @Autowired
  private LdapProperties ldapProperties;

  private List<String> findAndUpdateCurrentGroupsInLdap()
  {
    return ldapTemplate.search( query().where( "objectclass" ).is( ldapProperties.getGroupObjectClass() ),
        new LocalGroupLdapIdAttributesMapper() );
  }

  private class LocalGroupLdapIdAttributesMapper implements AttributesMapper<String>
  {
    public String mapFromAttributes( Attributes attrs ) throws NamingException
    {
      String ldapId = (String) attrs.get( ldapProperties.getUniqueGroupEntryAttribute() ).get();

      // Find the localGroup in the database
      LocalGroup localGroup = getLocalGroupRepository().findByLdapId( ldapId );

      // If it doesn't exist, create it.
      if ( localGroup == null )
      {
        localGroup = new LocalGroup();
        localGroup.setLdapId( ldapId );
      }

      String name = (String) attrs.get( ldapProperties.getGroupNameEntryAttribute() ).get();

      if ( !name.equals( localGroup.getName() ) )
      {
        // Update the name attribute
        localGroup.setName( ldapId );
        // Store the changes
        save( localGroup );
      }
      return ldapId;
    }
  }

  // Every minute
  @Scheduled( fixedDelayString = "60000" )
  @Transactional
  public void synchronizeLdapGroups()
  {
    List<String> currentGroupsInLdap = findAndUpdateCurrentGroupsInLdap();

    // Delete groups in local database not available in LDAP.
    if ( currentGroupsInLdap != null && !currentGroupsInLdap.isEmpty() )
    {
      getLocalGroupRepository().deleteByLdapIdNotIn( currentGroupsInLdap );
    }
  }

}
