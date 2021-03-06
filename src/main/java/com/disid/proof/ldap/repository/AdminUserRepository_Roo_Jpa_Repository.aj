// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.disid.proof.ldap.repository;

import com.disid.proof.ldap.model.AdminUser;
import com.disid.proof.ldap.repository.AdminUserRepository;
import com.disid.proof.ldap.repository.AdminUserRepositoryCustom;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.transaction.annotation.Transactional;

privileged aspect AdminUserRepository_Roo_Jpa_Repository {
    
    declare parents: AdminUserRepository extends DetachableJpaRepository<AdminUser, Long>;
    
    declare parents: AdminUserRepository extends AdminUserRepositoryCustom;
    
    declare @type: AdminUserRepository: @Transactional(readOnly = true);
    
}
