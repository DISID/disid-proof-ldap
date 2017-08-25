package com.disid.proof.ldap.integration.ldap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.disid.proof.ldap.model.LocalUser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith( SpringRunner.class )
@SpringBootTest
@ActiveProfiles( "dev" )
public class LdapUserServiceImplIT
{
  @Autowired
  private LdapTemplate ldapTemplate;

  @Mock
  private LocalDataProvider<LocalUser> provider;

  private LdapUserServiceImpl service;

  private LocalUser ben;

  private LocalUser bob;

  private LocalUser jerry;

  private LocalUser slashguy;

  private LocalUser createTest;

  @Before
  public void setup()
  {
    service = new LdapUserServiceImpl( ldapTemplate, "person", "uid", "cn" );

    ben = new LocalUser();
    ben.setLdapId( "ben" );
    ben.setName( "Ben Alex" );

    bob = new LocalUser();
    bob.setLdapId( "bob" );
    // Name different from the one in LDAP so it has to be updated
    bob.setName( "Old name" );

    jerry = new LocalUser();
    jerry.setLdapId( "jerry" );
    // Empty name different from the one in LDAP so it has to be updated
    jerry.setName( null );

    slashguy = new LocalUser();
    slashguy.setLdapId( "slashguy" );
    slashguy.setName( "slash/guy" );

    createTest = new LocalUser();
    createTest.setLdapId( "createtest" );
    createTest.setName( "Test name" );

    when( provider.getOrCreateByLdapId( ben.getLdapId() ) ).thenReturn( ben );
    when( provider.getOrCreateByLdapId( bob.getLdapId() ) ).thenReturn( bob );
    when( provider.getOrCreateByLdapId( jerry.getLdapId() ) ).thenReturn( jerry );
    when( provider.getOrCreateByLdapId( slashguy.getLdapId() ) ).thenReturn( slashguy );
    when( provider.getOrCreateByLdapId( createTest.getLdapId() ) ).thenReturn( createTest );
  }

  @Test
  public void findAndUpdateLocalLoadsAndUpdatesExpectedValues()
  {
    List<String> values = service.findAndUpdateLocal( provider );

    assertThat( values ).isNotEmpty();

    verify( provider ).saveFromLdap( bob );
    verify( provider ).saveFromLdap( jerry );
  }

  @Test
  public void deleteRemovesUserFromLdap()
  {
    List<String> initialValues = service.findAndUpdateLocal( provider );

    service.delete( ben );

    List<String> values = service.findAndUpdateLocal( provider );
    assertThat( values ).isNotEmpty().hasSize( initialValues.size() - 1 );
  }

  @Test
  public void saveAddsUserToLdap()
  {
    List<String> initialValues = service.findAndUpdateLocal( provider );

    service.save( createTest );

    List<String> values = service.findAndUpdateLocal( provider );
    assertThat( values ).isNotEmpty().hasSize( initialValues.size() + 1 );
  }

}
