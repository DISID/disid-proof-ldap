package com.disid.proof.ldap.config;

import com.disid.proof.ldap.service.api.AdminUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
  /**
   * Administration Users service to obtain user info to check by {@link AuthenticationProvider}'s
   */
  @Autowired
  private AdminUserService adminUserService;

  @Autowired
  private DefaultSpringSecurityContextSource contextSource;

  /**
   * LDAP properties
   */
  @Autowired
  private LdapProperties ldapProperties;


  @Override
  protected void configure( HttpSecurity http ) throws Exception
  {
    http.authorizeRequests().anyRequest().fullyAuthenticated().and().formLogin();
  }

  @Override
  public void configure( AuthenticationManagerBuilder auth ) throws Exception
  {

    // To have many authentication options, just add them to the AuthenticationManagerBuilder in
    // the order to be checked

    auth.authenticationProvider( defaultAdminAuthenticationProvider() );

    LdapProperties.Auth authProps = ldapProperties.getAuth();

    // @formatter:off
    auth.ldapAuthentication()
        .userDnPatterns( authProps.getUserDnPatterns() )
        .userSearchBase( authProps.getUserSearchBase() )
        .userSearchFilter( authProps.getUserSearchFilter() )
        .groupSearchBase( authProps.getGroupSearchBase() )
        .groupSearchFilter( authProps.getGroupSearchFilter() )
        .groupRoleAttribute( authProps.getGroupRoleAttribute() )
        .contextSource( contextSource )
        .passwordCompare()
        .passwordEncoder( new LdapShaPasswordEncoder() )
        .passwordAttribute( authProps.getPasswordAttribute() );
    // @formatter:on
  }

  /**
   * Create a default {@link AuthenticationProvider} bean for default CAM administrator.
   *
   * @return {@link AuthenticationProvider}
   */
  @Bean
  public AuthenticationProvider defaultAdminAuthenticationProvider()
  {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService( adminUserService );
    provider.setPasswordEncoder( new BCryptPasswordEncoder() );
    return provider;
  }

}
