package com.disid.proof.ldap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

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

  @Test
  public void loginWithValidLdapUserThenAuthenticated() throws Exception
  {
    FormLoginRequestBuilder login = formLogin().user( "ben" ).password( "benspassword" );

    mockMvc.perform( login ).andExpect( authenticated().withUsername( "ben" ) );
  }

  @Test
  public void loginWithValidMemoryUserThenAuthenticated() throws Exception
  {
    FormLoginRequestBuilder login = formLogin().user( "user" ).password( "password" );

    mockMvc.perform( login ).andExpect( authenticated().withUsername( "user" ) );
  }

  @Test
  public void loginWithInvalidUserThenUnauthenticated() throws Exception
  {
    FormLoginRequestBuilder login = formLogin().user( "invalid" ).password( "invalidpassword" );

    mockMvc.perform( login ).andExpect( unauthenticated() );
  }
}
