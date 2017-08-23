package com.disid.proof.ldap.integration.ldap;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import com.disid.proof.ldap.config.LdapProperties;
import com.disid.proof.ldap.model.LocalUser;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapName;

@Service
@Transactional
public class LdapUserServiceImpl implements LdapService<LocalUser>
{
  private final LdapTemplate ldapTemplate;

  private final LdapProperties ldapProperties;

  public LdapUserServiceImpl( LdapTemplate ldapTemplate, LdapProperties ldapProperties )
  {
    this.ldapTemplate = ldapTemplate;
    this.ldapProperties = ldapProperties;
  }

  @Override
  public List<String> findAndUpdateLocal( LocalDataProvider<LocalUser> provider )
  {
    LdapName usersBaseDN = LdapNameBuilder.newInstance().add( "ou", "people" ).build();
    return ldapTemplate.search(
        query().base( usersBaseDN ).where( "objectclass" ).is( ldapProperties.getUserObjectClass() ),
        new LocalUserLdapIdAttributesMapper( provider ) );
  }

  private class LocalUserLdapIdAttributesMapper implements AttributesMapper<String>
  {

    private final LocalDataProvider<LocalUser> provider;

    public LocalUserLdapIdAttributesMapper( LocalDataProvider<LocalUser> provider )
    {
      this.provider = provider;
    }

    public String mapFromAttributes( Attributes attrs ) throws NamingException
    {
      String ldapId = (String) attrs.get( ldapProperties.getUniqueUserEntryAttribute() ).get();

      // Find in the application database
      LocalUser localUser = provider.getOrCreateByLdapId( ldapId );

      String name = (String) attrs.get( ldapProperties.getUserNameEntryAttribute() ).get();

      if ( !name.equals( localUser.getName() ) )
      {
        // Update the name attribute
        localUser.setName( ldapId );
        // Store the changes
        provider.saveFromLdap( localUser );
      }
      return ldapId;
    }
  }

  @Override
  public void save( LocalUser group )
  {
    throw new UnsupportedOperationException( "Not implemented" );
  }

  @Override
  public void delete( LocalUser group )
  {
    throw new UnsupportedOperationException( "Not implemented" );
  }

}
