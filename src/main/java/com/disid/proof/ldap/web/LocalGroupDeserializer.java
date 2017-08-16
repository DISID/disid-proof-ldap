package com.disid.proof.ldap.web;
import com.disid.proof.ldap.model.LocalGroup;
import com.disid.proof.ldap.service.api.LocalGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooDeserializer;

/**
 * = LocalGroupDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = LocalGroup.class)
public class LocalGroupDeserializer extends JsonObjectDeserializer<LocalGroup> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private LocalGroupService localGroupService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param localGroupService
     * @param conversionService
     */
    @Autowired
    public LocalGroupDeserializer(@Lazy LocalGroupService localGroupService, ConversionService conversionService) {
        this.localGroupService = localGroupService;
        this.conversionService = conversionService;
    }
}
