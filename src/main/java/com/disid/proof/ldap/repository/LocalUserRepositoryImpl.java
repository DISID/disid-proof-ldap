package com.disid.proof.ldap.repository;

import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.disid.proof.ldap.model.LocalUser;

/**
 * = LocalUserRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */ 
@RooJpaRepositoryCustomImpl(repository = LocalUserRepositoryCustom.class)
public class LocalUserRepositoryImpl extends QueryDslRepositorySupportExt<LocalUser> {

    /**
     * TODO Auto-generated constructor documentation
     */
    LocalUserRepositoryImpl() {
        super(LocalUser.class);
    }
}