package com.disid.proof.ldap.integration.ldap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.disid.proof.ldap.model.LocalGroup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith( SpringRunner.class )
@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@ActiveProfiles( "dev" )
public class LdapGroupServiceImplIT
{
  private static final String[] GROUP_IDS = new String[] { "developers", "managers", "submanagers" };

  @SpyBean
  private LocalDataProvider<LocalGroup> provider;

  @Autowired
  private LdapGroupServiceImpl service;

  @Test
  public void synchronizeUpdatesExpectedValues()
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

    List<String> values = service.synchronize( provider );

    assertThat( values ).isNotEmpty().hasSize( GROUP_IDS.length );

    verify( provider ).getOrCreateByLdapId( GROUP_IDS[0] );
    verify( provider ).getOrCreateByLdapId( GROUP_IDS[1] );
    verify( provider ).getOrCreateByLdapId( GROUP_IDS[2] );
    verify( provider ).saveFromLdap( group2 );
    verify( provider ).saveFromLdap( group3 );
    verify( provider ).deleteByLdapIdNotIn( Arrays.asList( GROUP_IDS ) );
  }

}
