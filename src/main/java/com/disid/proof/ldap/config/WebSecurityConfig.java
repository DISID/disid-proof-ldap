package com.disid.proof.ldap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

import java.util.Arrays;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{

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

    auth.inMemoryAuthentication().withUser( "user" ).password( "password" ).roles( "USER" ).and().withUser( "admin" )
        .password( "password" ).roles( "USER", "ADMIN" );

    auth.ldapAuthentication().userDnPatterns( "uid={0},ou=people" ).groupSearchBase( "ou=groups" )
        .contextSource( contextSource() ).passwordCompare().passwordEncoder( new LdapShaPasswordEncoder() )
        .passwordAttribute( "userPassword" );
  }

  @Bean
  public DefaultSpringSecurityContextSource contextSource()
  {
    return new DefaultSpringSecurityContextSource( Arrays.asList( "ldap://localhost:8389/" ),
        "dc=springframework,dc=org" );
  }

}
