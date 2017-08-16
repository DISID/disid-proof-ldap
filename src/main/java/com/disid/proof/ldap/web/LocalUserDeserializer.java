package com.disid.proof.ldap.web;
import com.disid.proof.ldap.model.LocalUser;
import com.disid.proof.ldap.service.api.LocalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooDeserializer;

/**
 * = LocalUserDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = LocalUser.class)
public class LocalUserDeserializer extends JsonObjectDeserializer<LocalUser> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private LocalUserService localUserService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param localUserService
     * @param conversionService
     */
    @Autowired
    public LocalUserDeserializer(@Lazy LocalUserService localUserService, ConversionService conversionService) {
        this.localUserService = localUserService;
        this.conversionService = conversionService;
    }
}
