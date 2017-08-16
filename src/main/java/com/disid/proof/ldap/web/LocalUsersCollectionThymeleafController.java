package com.disid.proof.ldap.web;
import com.disid.proof.ldap.model.LocalUser;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;

/**
 * = LocalUsersCollectionThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = LocalUser.class, type = ControllerType.COLLECTION)
@RooThymeleaf
public class LocalUsersCollectionThymeleafController {
}
