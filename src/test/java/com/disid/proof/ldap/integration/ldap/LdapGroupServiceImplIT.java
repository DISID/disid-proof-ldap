package com.disid.proof.ldap.integration.ldap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.disid.proof.ldap.model.LocalGroup;

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
public class LdapGroupServiceImplIT
{
  private static final String[] GROUP_IDS = new String[] { "developers", "managers", "submanagers" };

  @Autowired
  private LdapTemplate ldapTemplate;

  @Mock
  private LocalDataProvider<LocalGroup> provider;

  private LdapGroupServiceImpl service;

  @Before
  public void setup()
  {
    service = new LdapGroupServiceImpl( ldapTemplate, "groupOfUniqueNames", "cn", "cn" );
  }

  @Test
  public void findAndUpdateLoadsAndUpdatesExpectedValues()
  {
    LocalGroup group1 = new LocalGroup();
    group1.setLdapId( GROUP_IDS[0] );
    group1.setName( "developers" );
    LocalGroup group2 = new LocalGroup();
    group2.setLdapId( GROUP_IDS[1] );
    // Name different from the one in LDAP so it has to be updated
    group2.setName( "the managers" );
    LocalGroup group3 = new LocalGroup();
    group3.setLdapId( GROUP_IDS[2] );
    // Empty name different from the one in LDAP so it has to be updated
    group3.setName( null );

    when( provider.getOrCreateByLdapId( GROUP_IDS[0] ) ).thenReturn( group1 );
    when( provider.getOrCreateByLdapId( GROUP_IDS[1] ) ).thenReturn( group2 );
    when( provider.getOrCreateByLdapId( GROUP_IDS[2] ) ).thenReturn( group3 );

    List<String> values = service.findAndUpdateLocal( provider );

    assertThat( values ).isNotEmpty().hasSize( GROUP_IDS.length );

    verify( provider ).saveFromLdap( group2 );
    verify( provider ).saveFromLdap( group3 );
  }

}
