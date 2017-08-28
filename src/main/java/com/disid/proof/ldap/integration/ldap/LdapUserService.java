package com.disid.proof.ldap.integration.ldap;

import com.disid.proof.ldap.model.LocalUser;

public interface LdapUserService extends LdapService<LocalUser>
{

  /**
   * Adds or updates the LDAP entry related to the given local entity.
   * @param element to save the related LDAP entry
   */
  void create( LocalUser element, String password );

  void changePassword( LocalUser element, String password );

}
