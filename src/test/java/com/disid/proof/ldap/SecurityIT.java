package com.disid.proof.ldap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

import com.disid.proof.ldap.integration.ldap.LdapUserService;
import com.disid.proof.ldap.model.LocalUser;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

public class SecurityIT extends AbstractLdapIT
{

  @Autowired
  private LdapUserService ldapUserService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void loginWithValidLdapUserThenAuthenticated() throws Exception
  {
    ldapUserService.synchronize( userProvider );

    FormLoginRequestBuilder login = formLogin().user( "ben" ).password( "benspassword" );

    mockMvc.perform( login ).andExpect( authenticated().withUsername( "ben" ) );
  }

  @Test
  @Transactional
  public void createUserThenLoginThenAuthenticated() throws Exception
  {
    LocalUser user = new LocalUser();
    user.setName( "newuser" );
    user.setLdapId( "newuser" );
    user.setBlocked( false );
    user.setNewRegistration( false );

    ldapUserService.create( user, "thepassword" );
    userProvider.saveFromLdap( user );

    ldapUserService.synchronize( userProvider );

    FormLoginRequestBuilder login = formLogin().user( "newuser" ).password( "thepassword" );

    mockMvc.perform( login ).andExpect( authenticated().withUsername( "newuser" ) );
  }


  @Test
  @Transactional
  public void changePasswordThenLoginThenAuthenticated() throws Exception
  {
    LocalUser ben = userProvider.getByLdapId( "ben" );

    ldapUserService.changePassword( ben, "newpassword" );

    FormLoginRequestBuilder login = formLogin().user( "ben" ).password( "newpassword" );

    mockMvc.perform( login ).andExpect( authenticated().withUsername( "ben" ) );
  }

  @Test
  @Transactional
  public void createUserThenLoginChangePasswordThenLoginThenAuthenticated() throws Exception
  {
    LocalUser user = new LocalUser();
    user.setName( "newuser2" );
    user.setLdapId( "newuser2" );
    user.setBlocked( false );
    user.setNewRegistration( false );

    ldapUserService.create( user, "thepassword" );
    userProvider.saveFromLdap( user );
    ldapUserService.synchronize( userProvider );

    FormLoginRequestBuilder login = formLogin().user( "newuser2" ).password( "thepassword" );

    mockMvc.perform( login ).andExpect( authenticated().withUsername( "newuser2" ) );

    mockMvc.perform( logout() );

    ldapUserService.changePassword( user, "newpassword" );

    FormLoginRequestBuilder login2 = formLogin().user( "newuser2" ).password( "newpassword" );

    mockMvc.perform( login2 ).andExpect( authenticated().withUsername( "newuser2" ) );
  }

  @Test
  public void loginWithValidAdminUserThenAuthenticated() throws Exception
  {
    FormLoginRequestBuilder login = formLogin().user( "admin" ).password( "p4ssWord" );

    mockMvc.perform( login ).andExpect( authenticated().withUsername( "admin" ) );
  }

  @Test
  public void loginWithInvalidUserThenUnauthenticated() throws Exception
  {
    FormLoginRequestBuilder login = formLogin().user( "invalid" ).password( "invalidpassword" );

    mockMvc.perform( login ).andExpect( unauthenticated() );
  }
}
