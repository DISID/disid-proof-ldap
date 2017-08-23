package com.disid.proof.ldap.integration.ldap;

import java.util.List;

public interface LdapService<T>
{

  List<String> findAndUpdateLocal( LocalDataProvider<T> provider );

  void save( T group );

  void delete( T group );

}
