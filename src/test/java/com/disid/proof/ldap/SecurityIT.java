package com.disid.proof.ldap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

import com.disid.proof.ldap.integration.ldap.LdapService;
import com.disid.proof.ldap.integration.ldap.LocalDataProvider;
import com.disid.proof.ldap.model.LocalUser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles( "dev" )
public class SecurityIT
{
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private LdapService<LocalUser> ldapUserService;

  @Autowired
  private LocalDataProvider<LocalUser> provider;

  @Test
  public void loginWithValidLdapUserThenAuthenticated() throws Exception
  {
    ldapUserService.synchronize( provider );

    FormLoginRequestBuilder login = formLogin().user( "ben" ).password( "benspassword" );

    mockMvc.perform( login ).andExpect( authenticated().withUsername( "ben" ) );
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
