package com.disid.proof.ldap.config;

import com.disid.proof.ldap.integration.ldap.LdapGroupServiceImpl;
import com.disid.proof.ldap.integration.ldap.LdapService;
import com.disid.proof.ldap.integration.ldap.LdapUserServiceImpl;
import com.disid.proof.ldap.model.LocalGroup;
import com.disid.proof.ldap.model.LocalUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

/**
 * Configuration of LDAP services.
 */
@Configuration
public class LdapConfiguration
{

  /**
   * LDAP configuration properties
   */
  private final LdapProperties ldapProperties;

  /**
   * Constructor. As it is the single constructor of the class, it is 
   * used automatically by Spring to autowire the parameters.
   * @param ldapProperties LDAP configuration properties
   */
  public LdapConfiguration( LdapProperties ldapProperties )
  {
    this.ldapProperties = ldapProperties;
  }

  /**
   * Creates the LDAP context source with the parameters to connect to the LDAP server.
   *
   * @return {@link DefaultSpringSecurityContextSource}
   */
  @Bean
  public DefaultSpringSecurityContextSource contextSource()
  {
    DefaultSpringSecurityContextSource contextSource = null;

    LdapProperties.Context context = ldapProperties.getContext();

    if ( StringUtils.isNotEmpty( context.getUrl() ) )
    {
      contextSource = new DefaultSpringSecurityContextSource( context.getUrl() );
      contextSource.setBase( context.getBaseDn() );
      contextSource.setUserDn( context.getUserDn() );
      contextSource.setPassword( context.getPassword() );
    }
    return contextSource;
  }

  /**
   * Creates a {@link LdapTemplate} for Active Directory (AD).
   *
   * @return the template to perform LDAP operations
   */
  @Bean
  public LdapTemplate ldapTemplate()
  {
    LdapTemplate ldap = new LdapTemplate( contextSource() );
    // For Active Directory (AD) users. See LdapTemplate doc.
    ldap.setIgnorePartialResultException( true );
    return ldap;
  }

  /**
   * Returns the service to manage users in the LDAP service.
   * @return the LDAP users service
   */
  @Bean
  public LdapService<LocalUser> ldapUserService()
  {
    LdapProperties.Sync.User user = ldapProperties.getSync().getUser();
    return new LdapUserServiceImpl( ldapTemplate(), user.getObjectClass(), user.getIdAttribute(),
        user.getNameAttribute(), ldapProperties.getAuth().getPasswordAttribute(), user.getObjectClassValues(),
        user.getBaseRdn() );
  }

  /**
   * Returns the service to manage groups in the LDAP service.
   * @return the LDAP groups service
   */
  @Bean
  public LdapService<LocalGroup> ldapGroupService()
  {
    LdapProperties.Sync.Group group = ldapProperties.getSync().getGroup();
    return new LdapGroupServiceImpl( ldapTemplate(), group.getObjectClass(), group.getIdAttribute(),
        group.getNameAttribute() );
  }

}
