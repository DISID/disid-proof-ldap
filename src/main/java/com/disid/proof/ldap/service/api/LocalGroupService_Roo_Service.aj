// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.disid.proof.ldap.service.api;

import com.disid.proof.ldap.model.LocalGroup;
import com.disid.proof.ldap.service.api.LocalGroupService;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.format.EntityResolver;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

privileged aspect LocalGroupService_Roo_Service {
    
    declare parents: LocalGroupService extends EntityResolver<LocalGroup, Long>;
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param id
     * @return LocalGroup
     */
    public abstract LocalGroup LocalGroupService.findOne(Long id);
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param localGroup
     */
    public abstract void LocalGroupService.delete(LocalGroup localGroup);
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param entities
     * @return List
     */
    public abstract List<LocalGroup> LocalGroupService.save(Iterable<LocalGroup> entities);
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param ids
     */
    public abstract void LocalGroupService.delete(Iterable<Long> ids);
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param entity
     * @return LocalGroup
     */
    public abstract LocalGroup LocalGroupService.save(LocalGroup entity);
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param id
     * @return LocalGroup
     */
    public abstract LocalGroup LocalGroupService.findOneForUpdate(Long id);
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param ids
     * @return List
     */
    public abstract List<LocalGroup> LocalGroupService.findAll(Iterable<Long> ids);
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @return List
     */
    public abstract List<LocalGroup> LocalGroupService.findAll();
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @return Long
     */
    public abstract long LocalGroupService.count();
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<LocalGroup> LocalGroupService.findAll(GlobalSearch globalSearch, Pageable pageable);
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param localGroup
     * @param usersToAdd
     * @return LocalGroup
     */
    public abstract LocalGroup LocalGroupService.addToUsers(LocalGroup localGroup, Iterable<Long> usersToAdd);
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param localGroup
     * @param usersToRemove
     * @return LocalGroup
     */
    public abstract LocalGroup LocalGroupService.removeFromUsers(LocalGroup localGroup, Iterable<Long> usersToRemove);
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param localGroup
     * @param users
     * @return LocalGroup
     */
    public abstract LocalGroup LocalGroupService.setUsers(LocalGroup localGroup, Iterable<Long> users);
    
}
