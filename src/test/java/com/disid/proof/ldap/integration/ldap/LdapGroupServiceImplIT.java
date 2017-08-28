package com.disid.proof.ldap.integration.ldap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.disid.proof.ldap.AbstractLdapIT;
import com.disid.proof.ldap.model.LocalGroup;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

public class LdapGroupServiceImplIT extends AbstractLdapIT
{
  private static final String[] GROUP_IDS = new String[] { "developers", "managers", "submanagers" };

  @Autowired
  private LdapService<LocalGroup> ldapGroupService;

  @Test
  @Transactional
  public void synchronizeUpdatesExpectedValues()
  {
    List<String> values = ldapGroupService.synchronize( groupProvider );

    assertThat( values ).isNotEmpty().hasSize( GROUP_IDS.length );

    verify( groupProvider ).deleteByLdapIdNotIn( Arrays.asList( GROUP_IDS ) );
  }

}
