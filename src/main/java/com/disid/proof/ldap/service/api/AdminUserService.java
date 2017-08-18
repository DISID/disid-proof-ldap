package com.disid.proof.ldap.service.api;

import com.disid.proof.ldap.model.AdminUser;

import org.springframework.roo.addon.layers.service.annotations.RooService;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * = AdminUserService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService( entity = AdminUser.class )
public interface AdminUserService
    extends UserDetailsService, AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>
{
}
