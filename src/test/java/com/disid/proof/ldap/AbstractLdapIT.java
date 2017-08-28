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

/**
 * When running ITs, the spring context is loaded for each case where the combination of
 * components is different. In this case, the ITs load the context of all the application,
 * so if you extend from this class, all the tests will be run from a single context,
 * having a big impact in the time it takes to run all the tests overall.
 * 
 * Also if you have any component loaded using a @SpyBean or @MockBean annotation, it will
 * impact the context configuration, so to still have a single context to run all tests, 
 * add those components in this class.
 *
 */
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
