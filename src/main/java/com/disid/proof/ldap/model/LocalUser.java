package com.disid.proof.ldap.model;

import org.springframework.roo.addon.javabean.annotations.RooEquals;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * = LocalUser
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean
@RooToString
@RooJpaEntity
@RooEquals( isJpaEntity = true )
public class LocalUser
{

  /**
   * TODO Auto-generated attribute documentation
   *
   */
  @Id
  @GeneratedValue( strategy = GenerationType.AUTO )
  private Long id;

  /**
   * TODO Auto-generated attribute documentation
   *
   */
  @Version
  private Integer version;

  /**
   * User name
   */
  @NotNull
  @Size( max = 255 )
  private String name;

  /**
   * Code to identify the user when accessing the system
   */
  @NotNull
  @Size( max = 255 )
  @Column( unique = true )
  @Pattern( regexp = "^[a-zA-Z0-9]*$" )
  private String login;

  /**
   * User cannot access the system
   */
  private boolean blocked;

  /**
   * User should change password when logs into any module
   */
  private boolean newRegistration;
}
