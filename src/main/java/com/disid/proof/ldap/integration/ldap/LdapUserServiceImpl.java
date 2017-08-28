package com.disid.proof.ldap.integration.ldap;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import com.disid.proof.ldap.model.LocalUser;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapName;

/**
 * {@link LdapService} implementation to manage LDAP entries related to the {@link LocalUser}
 * entity.
 */
@Transactional
public class LdapUserServiceImpl implements LdapUserService
{
  private final LdapTemplate ldapTemplate;

  private final String objectClass;

  private final String idAttribute;

  private final String nameAttribute;

  private final String[] objectClassValues;

  private final String baseRdn;

  private final String passwordAttribute;

  private final LdapShaPasswordEncoder encoder = new LdapShaPasswordEncoder();

  /**
   * Creates a new instance with the given configuration.
   * @param ldapTemplate to perform LDAP operations
   * @param objectClass of the LDAP entry related to the {@link LocalUser} entity
   * @param idAttribute the LDAP entry attribute which uniquely identifies an entry from its siblings
   * @param nameAttribute the LDAP entry attribute where the user name is stored
   */
  public LdapUserServiceImpl( LdapTemplate ldapTemplate, String objectClass, String idAttribute, String nameAttribute,
      String passwordAttribute, String[] objectClassValues, String baseRdn )
  {
    this.ldapTemplate = ldapTemplate;
    this.objectClass = objectClass;
    this.idAttribute = idAttribute;
    this.nameAttribute = nameAttribute;
    this.passwordAttribute = passwordAttribute;
    this.objectClassValues = objectClassValues;
    this.baseRdn = baseRdn;
  }

  @Override
  public List<String> synchronize( LocalDataProvider<LocalUser> provider )
  {
    LdapName usersBaseDN = baseDnBuilder().build();
    List<String> ldapIds = ldapTemplate.search( query().base( usersBaseDN ).where( "objectclass" ).is( objectClass ),
        new LocalUserLdapIdAttributesMapper( provider ) );
    if ( ldapIds != null && !ldapIds.isEmpty() )
    {
      provider.deleteByLdapIdNotIn( ldapIds );
    }
    return ldapIds;
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
        localUser.setName( nameAttribute );
        localUser.setBlocked( false );
        localUser.setNewRegistration( true );
        // Store the changes
        provider.saveFromLdap( localUser );
      }
      return ldapId;
    }
  }

  @Override
  public void create( LocalUser user )
  {
    LdapName dn = buildDn( user );
    DirContextAdapter context = new DirContextAdapter( dn );

    context.setAttributeValues( "objectclass", objectClassValues );
    context.setAttributeValue( this.idAttribute, user.getLdapId() );
    context.setAttributeValue( this.nameAttribute, user.getName() );
    context.setAttributeValue( "sn", user.getName() );

    ldapTemplate.bind( context );
  }

  @Override
  public void create( LocalUser user, String password )
  {
    LdapName dn = buildDn( user );
    DirContextAdapter context = new DirContextAdapter( dn );

    context.setAttributeValues( "objectclass", objectClassValues );
    context.setAttributeValue( this.idAttribute, user.getLdapId() );
    context.setAttributeValue( this.nameAttribute, user.getName() );
    context.setAttributeValue( "sn", user.getName() );
    context.setAttributeValue( this.passwordAttribute, encoder.encodePassword( password, null ) );

    ldapTemplate.bind( context );
  }

  @Override
  public void update( LocalUser user )
  {
    LdapName dn = buildDn( user );
    DirContextOperations operations = ldapTemplate.lookupContext( dn );

    operations.setAttributeValue( this.idAttribute, user.getLdapId() );
    operations.setAttributeValue( this.nameAttribute, user.getName() );
    operations.setAttributeValue( "sn", user.getName() );

    ldapTemplate.modifyAttributes( operations );
  }

  @Override
  public void changePassword( LocalUser user, String password )
  {
    LdapName dn = buildDn( user );
    DirContextOperations operations = ldapTemplate.lookupContext( dn );

    operations.setAttributeValue( this.passwordAttribute, encoder.encodePassword( password, null ) );

    ldapTemplate.modifyAttributes( operations );
  }

  @Override
  public void delete( LocalUser user )
  {
    LdapName dn = buildDn( user );
    ldapTemplate.unbind( dn );
  }

  private LdapName buildDn( LocalUser user )
  {
    return baseDnBuilder().add( this.idAttribute, user.getLdapId() ).build();
  }

  private LdapNameBuilder baseDnBuilder()
  {
    return LdapNameBuilder.newInstance( baseRdn );
  }

}
