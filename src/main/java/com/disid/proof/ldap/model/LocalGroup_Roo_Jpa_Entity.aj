// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.disid.proof.ldap.model;

import com.disid.proof.ldap.model.LocalGroup;
import io.springlets.format.EntityFormat;
import javax.persistence.Entity;

privileged aspect LocalGroup_Roo_Jpa_Entity {
    
    declare @type: LocalGroup: @Entity;
    
    declare @type: LocalGroup: @EntityFormat;
    
    /**
     * TODO Auto-generated attribute documentation
     * 
     */
    public static final String LocalGroup.ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";
    
    /**
     * TODO Auto-generated attribute documentation
     * 
     */
    public static final String LocalGroup.ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";
    
}