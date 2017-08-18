package com.disid.proof.ldap.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    if ( StringUtils.isNotEmpty( ldapProperties.getUrl() ) )
    {
      contextSource = new DefaultSpringSecurityContextSource( ldapProperties.getUrl() );
      contextSource.setBase( ldapProperties.getBaseDn() );
      contextSource.setUserDn( ldapProperties.getUserDn() );
      contextSource.setPassword( ldapProperties.getPasswordLdap() );
    }
    return contextSource;
  }

  //  /**
  //   * Generate a {@link LdapTemplate} for Active Directory (AD).
  //   *
  //   * @return {@link LdapTemplate}
  //   */
  //  @Bean
  //  public LdapTemplate ldapTemplate()
  //  {
  //    LdapTemplate ldap = new LdapTemplate( contextSource() );
  //    // For Active Directory (AD) users. See LdapTemplate doc.
  //    ldap.setIgnorePartialResultException( true );
  //    return ldap;
  //  }

}
