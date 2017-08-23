package com.disid.proof.ldap.integration.ldap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.disid.proof.ldap.config.LdapProperties;
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
  private static final String[] USER_IDS = new String[] { "ben", "bob", "jerry", "slashguy" };

  @Autowired
  private LdapTemplate ldapTemplate;

  @Autowired
  private LdapProperties ldapProperties;

  @Mock
  private LocalDataProvider<LocalUser> provider;

  private LdapUserServiceImpl service;

  @Before
  public void setup()
  {
    service = new LdapUserServiceImpl( ldapTemplate, ldapProperties );
  }

  @Test
  public void findAndUpdateLocalLoadsAndUpdatesExpectedValues()
  {
    LocalUser user1 = new LocalUser();
    user1.setLdapId( USER_IDS[0] );
    user1.setName( "Ben Alex" );
    LocalUser user2 = new LocalUser();
    user2.setLdapId( USER_IDS[1] );
    // Name different from the one in LDAP so it has to be updated
    user2.setName( "Old name" );
    LocalUser user3 = new LocalUser();
    user3.setLdapId( USER_IDS[2] );
    // Empty name different from the one in LDAP so it has to be updated
    user3.setName( null );
    LocalUser user4 = new LocalUser();
    user4.setLdapId( USER_IDS[3] );
    user4.setName( "slash/guy" );

    when( provider.getOrCreateByLdapId( USER_IDS[0] ) ).thenReturn( user1 );
    when( provider.getOrCreateByLdapId( USER_IDS[1] ) ).thenReturn( user2 );
    when( provider.getOrCreateByLdapId( USER_IDS[2] ) ).thenReturn( user3 );
    when( provider.getOrCreateByLdapId( USER_IDS[3] ) ).thenReturn( user4 );

    List<String> values = service.findAndUpdateLocal( provider );

    assertThat( values ).isNotEmpty().hasSize( USER_IDS.length );

    verify( provider ).saveFromLdap( user2 );
    verify( provider ).saveFromLdap( user3 );
  }

}
