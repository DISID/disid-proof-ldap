package com.disid.proof.ldap.service.impl;

import com.disid.proof.ldap.integration.ldap.LdapService;
import com.disid.proof.ldap.integration.ldap.LocalDataProvider;
import com.disid.proof.ldap.model.LocalUser;
import com.disid.proof.ldap.service.api.LocalUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * = LocalUserServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl( service = LocalUserService.class )
public class LocalUserServiceImpl implements LocalUserService, LocalDataProvider<LocalUser>
{

  @Autowired
  private LdapService<LocalUser> ldapService;

  // Every 10 minutes
  @Scheduled( fixedDelayString = "600000" )
  @Transactional
  public void updateFromLdapUsers()
  {
    List<String> currentUsersInLdap = ldapService.findAndUpdateLocal( this );
    // Delete groups in local database not available in LDAP.
    if ( currentUsersInLdap != null && !currentUsersInLdap.isEmpty() )
    {
      getLocalUserRepository().deleteByLdapIdNotIn( currentUsersInLdap );
    }
  }

  @Override
  public LocalUser getOrCreateByLdapId( String ldapId )
  {
    LocalUser user = getLocalUserRepository().findByLdapId( ldapId );
    if ( user == null )
    {
      user = new LocalUser();
      user.setLdapId( ldapId );
    }
    return user;
  }

  @Override
  public void saveFromLdap( LocalUser localUser )
  {
    getLocalUserRepository().save( localUser );
  }

  /**
   * TODO Auto-generated method documentation
   *
   * @param entity
   * @return LocalUser
   */
  @Transactional
  public LocalUser save( LocalUser entity )
  {
    LocalUser localUser = getLocalUserRepository().save( entity );
    ldapService.save( localUser );
    return localUser;
  }

  /**
   * TODO Auto-generated method documentation
   *
   * @param localUser
   */
  @Transactional
  public void delete( LocalUser localUser )
  {
    getLocalUserRepository().delete( localUser );
    ldapService.delete( localUser );
  }
}
