package com.disid.proof.ldap.web;
import com.disid.proof.ldap.model.LocalGroup;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;

/**
 * = LocalGroupsCollectionThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = LocalGroup.class, type = ControllerType.COLLECTION)
@RooThymeleaf
public class LocalGroupsCollectionThymeleafController {
}
