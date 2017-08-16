package com.disid.proof.ldap.repository;

import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.disid.proof.ldap.model.LocalGroup;

/**
 * = LocalGroupRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */ 
@RooJpaRepositoryCustomImpl(repository = LocalGroupRepositoryCustom.class)
public class LocalGroupRepositoryImpl extends QueryDslRepositorySupportExt<LocalGroup> {

    /**
     * TODO Auto-generated constructor documentation
     */
    LocalGroupRepositoryImpl() {
        super(LocalGroup.class);
    }
}