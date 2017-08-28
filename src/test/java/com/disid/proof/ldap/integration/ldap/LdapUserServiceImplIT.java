package com.disid.proof.ldap.integration.ldap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.disid.proof.ldap.model.LocalUser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith( SpringRunner.class )
@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@ActiveProfiles( "dev" )
public class LdapUserServiceImplIT
{
  @SpyBean
  private LocalDataProvider<LocalUser> provider;

  @Autowired
  private LdapUserServiceImpl service;

  private LocalUser ben;
  
  private LocalUser createTest;

  @Before
  public void setup()
  {
    ben = new LocalUser();
    ben.setLdapId( "ben" );
    ben.setName( "Ben Alex" );

    createTest = new LocalUser();
    createTest.setLdapId( "createtest" );
    createTest.setName( "Test name" );
  }

  @Test
  @Transactional
  public void synchronizeUpdatesExpectedValues()
  {
    List<String> values = service.synchronize( provider );
    LocalUser bob = provider.getByLdapId( "bob" );
    LocalUser jerry = provider.getByLdapId( "jerry" );

    assertThat( values ).isNotEmpty();

    verify( provider ).saveFromLdap( bob );
    verify( provider ).saveFromLdap( jerry );
  }

  @Test
  @Transactional
  public void deleteRemovesUserFromLdap()
  {
    List<String> initialValues = service.synchronize( provider );

    service.delete( ben );

    List<String> values = service.synchronize( provider );
    assertThat( values ).isNotEmpty().hasSize( initialValues.size() - 1 );
  }

  @Test
  @Transactional
  public void saveAddsUserToLdap()
  {
    List<String> initialValues = service.synchronize( provider );

    service.create( createTest );

    List<String> values = service.synchronize( provider );
    assertThat( values ).isNotEmpty().hasSize( initialValues.size() + 1 );
  }

}
