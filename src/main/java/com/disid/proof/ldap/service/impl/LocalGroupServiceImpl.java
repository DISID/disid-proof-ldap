package com.disid.proof.ldap.service.impl;

import com.disid.proof.ldap.integration.ldap.LdapService;
import com.disid.proof.ldap.integration.ldap.LocalDataProvider;
import com.disid.proof.ldap.model.LocalGroup;
import com.disid.proof.ldap.service.api.LocalGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

/**
 * = LocalGroupServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl( service = LocalGroupService.class )
public class LocalGroupServiceImpl implements LocalGroupService, LocalDataProvider<LocalGroup>
{

  @Autowired
  private LdapService<LocalGroup> ldapService;

  // Every 10 minutes
  @Scheduled( fixedDelayString = "600000" )
  @Transactional
  public void synchronizeFromLdapGroups()
  {
    ldapService.synchronize( this );
  }

  @Override
  public void saveFromLdap( LocalGroup localGroup )
  {
    saveFromLdap( localGroup );
  }

  @Override
  public LocalGroup getOrCreateByLdapId( String ldapId )
  {
    LocalGroup group = getLocalGroupRepository().findByLdapId( ldapId );
    if ( group == null )
    {
      group = new LocalGroup();
      group.setLdapId( ldapId );
    }
    return group;
  }

  @Override
  public void deleteByLdapIdNotIn( Iterable<String> ldapIds )
  {
    getLocalGroupRepository().deleteByLdapIdNotIn( ldapIds );
  }
}
