package com.disid.proof.ldap.repository;
import com.disid.proof.ldap.model.AdminUser;

import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;

/**
 * = AdminUserRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = AdminUser.class)
public interface AdminUserRepository {

  AdminUser findByLogin( String login );
}
