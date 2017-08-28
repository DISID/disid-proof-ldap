package com.disid.proof.ldap.integration.ldap;

import java.util.List;

/**
 * Interface which define the options available to manage a type of entry in a LDAP service.
 * It is also used to perform some synchronization services with data stored in the 
 * LDAP service and data stored in the application.
 *
 * @param <T> the type of elements to manage
 */
public interface LdapService<T>
{

  /**
   * Updates the information stored in the application with the information available
   * in the LDAP server for the managed entry type.
   * While loading the data, it will use the given local data provider to load
   * the related data in the application. If the loaded entry has an entity with the
   * same identifier in the application, it will be updated with the data in the
   * LDAP service. 
   * Otherwise, it will be created.
   * Local elements whose identifier is not available in the LDAP server, will be deleted.
   * @param provider used to perform CRUD operations on the application data
   * @return the list of identifiers of the entries loaded from the LDAP server
   */
  List<String> synchronize( LocalDataProvider<T> provider );

  /**
   * Adds the LDAP entry related to the given local entity.
   * @param element to create the related LDAP entry
   */
  void create( T element );

  /**
   * Updates the LDAP entry related to the given local entity.
   * @param element to update the related LDAP entry
   */
  void update( T element );

  /**
   * Deletes the given element in the LDAP server
   * @param element to delete
   */
  void delete( T element );

}
