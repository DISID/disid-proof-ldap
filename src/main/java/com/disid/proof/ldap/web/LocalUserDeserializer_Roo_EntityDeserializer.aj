// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.disid.proof.ldap.web;

import com.disid.proof.ldap.model.LocalUser;
import com.disid.proof.ldap.service.api.LocalUserService;
import com.disid.proof.ldap.web.LocalUserDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import io.springlets.web.NotFoundException;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.core.convert.ConversionService;

privileged aspect LocalUserDeserializer_Roo_EntityDeserializer {
    
    declare @type: LocalUserDeserializer: @JsonComponent;
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @return LocalUserService
     */
    public LocalUserService LocalUserDeserializer.getLocalUserService() {
        return localUserService;
    }
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param localUserService
     */
    public void LocalUserDeserializer.setLocalUserService(LocalUserService localUserService) {
        this.localUserService = localUserService;
    }
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @return ConversionService
     */
    public ConversionService LocalUserDeserializer.getConversionService() {
        return conversionService;
    }
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param conversionService
     */
    public void LocalUserDeserializer.setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }
    
    /**
     * TODO Auto-generated method documentation
     * 
     * @param jsonParser
     * @param context
     * @param codec
     * @param tree
     * @return LocalUser
     * @throws IOException
     */
    public LocalUser LocalUserDeserializer.deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) throws IOException {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        LocalUser localUser = localUserService.findOne(id);
        if (localUser == null) {
            throw new NotFoundException("LocalUser not found");
        }
        return localUser;
    }
    
}