package com.disid.proof.ldap.integration.ldap;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import com.disid.proof.ldap.model.LocalUser;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapName;

@Transactional
public class LdapUserServiceImpl implements LdapService<LocalUser>
{
  private final LdapTemplate ldapTemplate;

  private final String objectClass;

  private final String idAttribute;

  private final String nameAttribute;

  public LdapUserServiceImpl( LdapTemplate ldapTemplate, String objectClass, String idAttribute, String nameAttribute )
  {
    this.ldapTemplate = ldapTemplate;
    this.objectClass = objectClass;
    this.idAttribute = idAttribute;
    this.nameAttribute = nameAttribute;
  }

  @Override
  public List<String> findAndUpdateLocal( LocalDataProvider<LocalUser> provider )
  {
    LdapName usersBaseDN = LdapNameBuilder.newInstance().add( "ou", "people" ).build();
    return ldapTemplate.search( query().base( usersBaseDN ).where( "objectclass" ).is( objectClass ),
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
      String ldapId = (String) attrs.get( idAttribute ).get();

      // Find in the application database
      LocalUser localUser = provider.getOrCreateByLdapId( ldapId );

      String name = (String) attrs.get( nameAttribute ).get();

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
  public void save( LocalUser user )
  {
    LdapName dn = buildDn( user );
    DirContextAdapter context = new DirContextAdapter( dn );

    context.setAttributeValues( "objectclass",
        new String[] { "top", "person", "organizationalPerson", "inetOrgPerson" } );
    context.setAttributeValue( this.idAttribute, user.getLdapId() );
    context.setAttributeValue( this.nameAttribute, user.getName() );
    context.setAttributeValue( "sn", user.getName() );

    ldapTemplate.bind( context );
  }

  @Override
  public void delete( LocalUser user )
  {
    LdapName dn = buildDn( user );
    ldapTemplate.unbind( dn );
  }

  private LdapName buildDn( LocalUser user )
  {
    return LdapNameBuilder.newInstance().add( "ou", "people" ).add( this.idAttribute, user.getLdapId() ).build();
  }

}
