package com.disid.proof.ldap.integration.ldap;

import java.util.List;

public interface LdapService<T>
{

  List<String> findAndUpdateLocal( LocalDataProvider<T> provider );

  void create( T group );

  void update( T group );

  void delete( T group );

}
