package com.disid.proof.ldap.integration.ldap;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import com.disid.proof.ldap.model.LocalGroup;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

@Transactional
public class LdapGroupServiceImpl implements LdapService<LocalGroup>
{
  private final LdapTemplate ldapTemplate;

  private final String objectClass;
  private final String idAttribute;
  private final String nameAttribute;

  public LdapGroupServiceImpl( LdapTemplate ldapTemplate, String objectClass, String idAttribute, String nameAttribute )
  {
    this.ldapTemplate = ldapTemplate;
    this.objectClass = objectClass;
    this.idAttribute = idAttribute;
    this.nameAttribute = nameAttribute;
  }

  @Override
  public List<String> findAndUpdateLocal( LocalDataProvider<LocalGroup> provider )
  {
    return ldapTemplate.search( query().where( "objectclass" ).is( objectClass ),
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
