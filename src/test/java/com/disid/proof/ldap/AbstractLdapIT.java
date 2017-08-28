package com.disid.proof.ldap;

import com.disid.proof.ldap.integration.ldap.LocalDataProvider;
import com.disid.proof.ldap.model.LocalGroup;
import com.disid.proof.ldap.model.LocalUser;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class )
@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@ActiveProfiles( "dev" )
public abstract class AbstractLdapIT
{
  @SpyBean
  protected LocalDataProvider<LocalUser> userProvider;

  @SpyBean
  protected LocalDataProvider<LocalGroup> groupProvider;

}
