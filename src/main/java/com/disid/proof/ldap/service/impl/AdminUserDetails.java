package com.disid.proof.ldap.service.impl;

import com.disid.proof.ldap.model.AdminUser;

import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class AdminUserDetails extends User
{

  private static final long serialVersionUID = 541352026298342794L;

  public AdminUserDetails( AdminUser user )
  {
    super( user.getLogin(), user.getPassword(), Collections.emptySet() );
  }

}
