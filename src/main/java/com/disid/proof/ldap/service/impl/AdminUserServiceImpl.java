package com.disid.proof.ldap.service.impl;

import com.disid.proof.ldap.model.AdminUser;
import com.disid.proof.ldap.service.api.AdminUserService;

import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * = AdminUserServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = AdminUserService.class)
public class AdminUserServiceImpl
    implements AdminUserService
{

  /**
   * Locates the user based on the username. In the actual implementation, the search
   * may possibly be case sensitive, or case insensitive depending on how the
   * implementation instance is configured. In this case, the <code>UserDetails</code>
   * object that comes back may have a username that is of a different case than what
   * was actually requested..
   *
   * @param username the username identifying the user whose data is required.
   *
   * @return a fully populated user record (never <code>null</code>)
   *
   * @throws UsernameNotFoundException if the user could not be found or the user has no
   * GrantedAuthority
   */
  public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException
  {

    AdminUser adminUser = getAdminUserRepository().findByLogin( username );

    if ( adminUser == null )
    {
      throw new UsernameNotFoundException( username );
    }

    return new AdminUserDetails( adminUser );

  }

  public UserDetails loadUserDetails( PreAuthenticatedAuthenticationToken token ) throws UsernameNotFoundException
  {
    return loadUserByUsername( (String) token.getPrincipal() );
  }
}
