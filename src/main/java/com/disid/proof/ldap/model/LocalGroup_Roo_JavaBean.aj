// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.disid.proof.ldap.model;

import com.disid.proof.ldap.model.LocalGroup;
import com.disid.proof.ldap.model.LocalUser;
import java.util.Set;

privileged aspect LocalGroup_Roo_JavaBean {
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @return Long
     */
    public Long LocalGroup.getId() {
        return this.id;
    }
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param id
     */
    public void LocalGroup.setId(Long id) {
        this.id = id;
    }
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @return String
     */
    public String LocalGroup.getLdapId() {
        return this.ldapId;
    }
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param ldapId
     */
    public void LocalGroup.setLdapId(String ldapId) {
        this.ldapId = ldapId;
    }
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @return Integer
     */
    public Integer LocalGroup.getVersion() {
        return this.version;
    }
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param version
     */
    public void LocalGroup.setVersion(Integer version) {
        this.version = version;
    }
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @return String
     */
    public String LocalGroup.getName() {
        return this.name;
    }
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param name
     */
    public void LocalGroup.setName(String name) {
        this.name = name;
    }
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @return Set
     */
    public Set<LocalUser> LocalGroup.getUsers() {
        return this.users;
    }
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param users
     */
    public void LocalGroup.setUsers(Set<LocalUser> users) {
        this.users = users;
    }
    
}
