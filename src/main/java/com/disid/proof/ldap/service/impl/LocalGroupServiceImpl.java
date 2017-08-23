package com.disid.proof.ldap.service.impl;

import com.disid.proof.ldap.integration.ldap.LdapService;
import com.disid.proof.ldap.integration.ldap.LocalDataProvider;
import com.disid.proof.ldap.model.LocalGroup;
import com.disid.proof.ldap.service.api.LocalGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
  public void updateFromLdapGroups()
  {
    List<String> currentGroupsInLdap = ldapService.findAndUpdateLocal( this );

    // Delete groups in local database not available in LDAP.
    if ( currentGroupsInLdap != null && !currentGroupsInLdap.isEmpty() )
    {
      getLocalGroupRepository().deleteByLdapIdNotIn( currentGroupsInLdap );
    }
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

}
