package com.disid.proof.ldap.integration.ldap;

/**
 * Manages local data for a {@link LdapService}. 
 *
 * @param <T> the type of local entity
 */
public interface LocalDataProvider<T>
{
  /**
   * Returns and existing instance of the entity with the given LDAP identifier
   * or a new instance if it still doesn't exist.
   * @param ldapId the identifier that relates the local entity with an LDAP entry
   * @return the existing or new local entity
   */
  T getOrCreateByLdapId( String ldapId );

  /**
   * Returns and existing instance of the entity with the given LDAP identifier.
   * @param ldapId the identifier that relates the local entity with an LDAP entry
   * @return the local entity or null
   */
  T getByLdapId( String ldapId );

  /**
   * Stores a new or updated entity with the values that come from a related LDAP entry.
   * @param value the entity to store in the local application repository
   */
  void saveFromLdap( T value );

  /**
   * Deletes all the local entities whose LDAP related identifier is not included in the list.
   * This is usually used to remove local entities whose related LDAP entry no longer exists
   * @param ldapIds the list of LDAP related identifier of entities not to delete
   */
  void deleteByLdapIdNotIn( Iterable<String> ldapIds );
}
