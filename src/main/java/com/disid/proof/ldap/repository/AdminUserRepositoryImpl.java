package com.disid.proof.ldap.repository;

import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.disid.proof.ldap.model.AdminUser;

/**
 * = AdminUserRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */ 
@RooJpaRepositoryCustomImpl(repository = AdminUserRepositoryCustom.class)
public class AdminUserRepositoryImpl extends QueryDslRepositorySupportExt<AdminUser> {

    /**
     * TODO Auto-generated constructor documentation
     */
    AdminUserRepositoryImpl() {
        super(AdminUser.class);
    }
}