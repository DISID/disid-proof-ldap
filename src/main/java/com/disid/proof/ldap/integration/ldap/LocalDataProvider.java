package com.disid.proof.ldap.integration.ldap;

public interface LocalDataProvider<T>
{
  T getOrCreateByLdapId( String ldapId );

  void saveFromLdap( T value );
}
