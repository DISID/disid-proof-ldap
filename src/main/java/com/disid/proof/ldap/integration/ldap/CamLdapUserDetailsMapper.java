/**
 * Copyright (c) 2016 DISID Corporation S.L. All rights reserved.
 */
package com.disid.proof.ldap.integration.ldap;

import com.disid.proof.ldap.model.LocalUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

import java.util.Collection;

/**
 * Maps a Spring Security {@link UserDetails} object from the data stored in the LDAP server,
 * as well as some information stored in the related {@link LocalUser} entity.
 */
public class CamLdapUserDetailsMapper extends LdapUserDetailsMapper
{

  private static final Logger LOG = LoggerFactory.getLogger( CamLdapUserDetailsMapper.class );

  private final LocalDataProvider<LocalUser> provider;
  private final String ldapIdAttribute;

  /**
   * Creates a new instance with the given configuration.
   * @param provider the manager of {@link LocalUser} data 
   * @param ldapIdAttribute the LDAP entry attribute which uniquely identifies an entry from its siblings
   */
  public CamLdapUserDetailsMapper( LocalDataProvider<LocalUser> provider, String ldapIdAttribute )
  {
    this.provider = provider;
    this.ldapIdAttribute = ldapIdAttribute;
  }

  @Override
  public UserDetails mapUserFromContext( DirContextOperations ctx, String username,
      Collection<? extends GrantedAuthority> authorities )
  {
    LdapUserDetails userDetails = (LdapUserDetails) super.mapUserFromContext( ctx, username, authorities );

    LdapUserDetailsImpl.Essence essence = new LdapUserDetailsImpl.Essence( userDetails );

    String ldapId = ctx.getStringAttribute( ldapIdAttribute );
    LocalUser user = provider.getByLdapId( ldapId );

    if ( user == null )
    {
      LOG.warn( "User with LDAP identifier {} tried to log into the application "
          + "but its data is still not synchronized with the local database", ldapId );
      essence.setEnabled( false );
    }
    else
    {
      essence.setEnabled( !user.isBlocked() );
    }

    return essence.createUserDetails();
  }

}
