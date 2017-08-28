package com.disid.proof.ldap.integration.ldap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.disid.proof.ldap.AbstractLdapIT;
import com.disid.proof.ldap.model.LocalUser;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class LdapUserServiceImplIT extends AbstractLdapIT
{

  @Autowired
  private LdapUserService ldapUserService;

  @Test
  @Transactional
  public void synchronizeUpdatesExpectedValues()
  {
    List<String> values = ldapUserService.synchronize( userProvider );
    LocalUser bob = userProvider.getByLdapId( "bob" );
    LocalUser jerry = userProvider.getByLdapId( "jerry" );

    assertThat( values ).isNotEmpty();

    verify( userProvider ).saveFromLdap( bob );
    verify( userProvider ).saveFromLdap( jerry );
  }

  @Test
  @Transactional
  public void deleteRemovesUserFromLdap()
  {

    List<String> initialValues = ldapUserService.synchronize( userProvider );

    LocalUser ben = new LocalUser();
    ben.setLdapId( "ben" );
    ben.setName( "Ben Alex" );
    ldapUserService.delete( ben );

    List<String> values = ldapUserService.synchronize( userProvider );
    assertThat( values ).isNotEmpty().hasSize( initialValues.size() - 1 );
  }

  @Test
  @Transactional
  public void saveAddsUserToLdap()
  {

    List<String> initialValues = ldapUserService.synchronize( userProvider );

    LocalUser createTest = new LocalUser();
    createTest.setLdapId( "createtest" );
    createTest.setName( "Test name" );
    ldapUserService.create( createTest );

    List<String> values = ldapUserService.synchronize( userProvider );
    assertThat( values ).isNotEmpty().hasSize( initialValues.size() + 1 );
  }

}
