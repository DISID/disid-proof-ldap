package com.disid.proof.ldap.repository;
import com.disid.proof.ldap.model.LocalGroup;

import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;

/**
 * = LocalGroupRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = LocalGroup.class)
public interface LocalGroupRepository {

  LocalGroup findByLdapId( String ldapId );

  void deleteByLdapIdNotIn( Iterable<String> ldapIds );
}
