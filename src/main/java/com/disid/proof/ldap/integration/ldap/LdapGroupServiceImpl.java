package com.disid.proof.ldap.integration.ldap;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import com.disid.proof.ldap.config.LdapProperties;
import com.disid.proof.ldap.model.LocalGroup;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

@Service
@Transactional
public class LdapGroupServiceImpl implements LdapService<LocalGroup>
{
  private final LdapTemplate ldapTemplate;

  private final LdapProperties ldapProperties;

  public LdapGroupServiceImpl( LdapTemplate ldapTemplate, LdapProperties ldapProperties )
  {
    this.ldapTemplate = ldapTemplate;
    this.ldapProperties = ldapProperties;
  }

  @Override
  public List<String> findAndUpdateLocal( LocalDataProvider<LocalGroup> provider )
  {
    return ldapTemplate.search( query().where( "objectclass" ).is( ldapProperties.getGroupObjectClass() ),
        new LocalGroupLdapIdAttributesMapper( provider ) );
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
      String ldapId = (String) attrs.get( ldapProperties.getUniqueGroupEntryAttribute() ).get();

      // Find in the application database
      LocalGroup localGroup = provider.getOrCreateByLdapId( ldapId );

      String name = (String) attrs.get( ldapProperties.getGroupNameEntryAttribute() ).get();

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
