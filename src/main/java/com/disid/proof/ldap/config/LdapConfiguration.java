package com.disid.proof.ldap.config;

import com.disid.proof.ldap.integration.ldap.LdapService;
import com.disid.proof.ldap.integration.ldap.LdapUserServiceImpl;
import com.disid.proof.ldap.model.LocalUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

/**
 * = LdapConfiguration
 *
 * LDAP Configuration
 */
@Configuration
public class LdapConfiguration
{

  /**
   * LDAP properties
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
   * Set base LDAP path context source.
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
   * Generate a {@link LdapTemplate} for Active Directory (AD).
   *
   * @return {@link LdapTemplate}
   */
  @Bean
  public LdapTemplate ldapTemplate()
  {
    LdapTemplate ldap = new LdapTemplate( contextSource() );
    // For Active Directory (AD) users. See LdapTemplate doc.
    ldap.setIgnorePartialResultException( true );
    return ldap;
  }

  @Bean
  public LdapService<LocalUser> ldapUserService()
  {
    LdapProperties.Sync.User user = ldapProperties.getSync().getUser();
    return new LdapUserServiceImpl( ldapTemplate(), user.getObjectClass(), user.getIdAttribute(),
        user.getNameAttribute() );
  }

}
