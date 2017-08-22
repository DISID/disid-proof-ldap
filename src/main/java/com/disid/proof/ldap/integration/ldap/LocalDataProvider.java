package com.disid.proof.ldap.integration.ldap;

public interface LocalDataProvider<T>
{
  T getOrCreate( String ldapId );

  void save( T value );
}
