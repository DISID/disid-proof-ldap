package com.disid.proof.ldap.integration.ldap;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import com.disid.proof.ldap.model.LocalGroup;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

/**
 * Service to manage groups in the LDAP service.
 */
@Transactional
public class LdapGroupServiceImpl implements LdapService<LocalGroup>
{
  private final LdapTemplate ldapTemplate;

  private final String objectClass;
  private final String idAttribute;
  private final String nameAttribute;

  /**
   * Creates a new service to manage groups in the LDAP server
   * @param ldapTemplate to perform LDAP operations
   * @param objectClass of the groups in the LDAP server
   * @param idAttribute attribute which identifies uniquely a group from its sibling entries
   * @param nameAttribute the attribute to use as the group's name
   */
  public LdapGroupServiceImpl( LdapTemplate ldapTemplate, String objectClass, String idAttribute, String nameAttribute )
  {
    this.ldapTemplate = ldapTemplate;
    this.objectClass = objectClass;
    this.idAttribute = idAttribute;
    this.nameAttribute = nameAttribute;
  }

  @Override
  public List<String> synchronize( LocalDataProvider<LocalGroup> provider )
  {
    List<String> ldapIds = ldapTemplate.search( query().where( "objectclass" ).is( objectClass ),
        new LocalGroupLdapIdAttributesMapper( provider ) );
    if ( ldapIds != null && !ldapIds.isEmpty() )
    {
      provider.deleteByLdapIdNotIn( ldapIds );
    }
    return ldapIds;
  }

  private class LocalGroupLdapIdAttributesMapper implements AttributesMapper<String>
  {

    private final LocalDataProvider<LocalGroup> provider;

    public LocalGroupLdapIdAttributesMapper( LocalDataProvider<LocalGroup> provider )
    {
      this.provider = provider;
    }

    public String mapFromAttributes( Attributes attrs ) throws NamingException
    {
      String ldapId = (String) attrs.get( idAttribute ).get();

      // Find in the application database
      LocalGroup localGroup = provider.getOrCreateByLdapId( ldapId );

      String name = (String) attrs.get( nameAttribute ).get();

      if ( !name.equals( localGroup.getName() ) )
      {
        // Update the name attribute
        localGroup.setName( ldapId );
        // Store the changes
        provider.saveFromLdap( localGroup );
      }
      return ldapId;
    }
  }

  @Override
  public void save( LocalGroup group )
  {
    throw new UnsupportedOperationException( "Not implemented" );
  }

  @Override
  public void delete( LocalGroup group )
  {
    throw new UnsupportedOperationException( "Not implemented" );
  }

}
