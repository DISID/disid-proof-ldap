package com.disid.proof.ldap.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * = LdapProperties
 *
 * LDAP properties
 */
@Component
@ConfigurationProperties( prefix = "application.ldap", ignoreUnknownFields = false )
public class LdapProperties
{

  /**
   * The URL to connect to the LDAP server.
   */
  private String url;

  /**
   * The user distinguished name (principal) to use for connecting to the LDAP server.
   * Defaults to "".
   */
  private String userDn = "";

  /**
   * The password to connect to the LDAP server.
   * Defaults to "".
   */
  private String password = "";

  /**
   * The base suffix from which all operations should origin. 
   * If a base suffix is set, you will not have to (and, indeed, must not) specify the full
   * distinguished names in any operations performed.
   * Defaults to "".
   */
  private String baseDn = "";

  /**
   * If your users are at a fixed location in the directory 
   * (i.e. you can work out the DN directly from the username without doing a directory search), 
   * you can use this attribute to map directly to the DN. It maps directly to the userDnPatterns
   * property of AbstractLdapAuthenticator. The value is a specific pattern used to build the
   * user's DN, for example "uid={0},ou=people".
   * The key "{0}" must be present and will be substituted with the username.
   * Defaults to "".
   */
  private String userDnPatterns = "";

  /**
   * The attribute in the directory which contains the user password. Defaults to "userPassword".
   */
  private String passwordAttribute = "userPassword";

  /**
   * The attribute in the user entries which uniquely identifies them from the sibling entries.
   * Defaults to 'cn'.
   */
  private String uniqueUserEntryAttribute = "cn";

  /**
   * The attribute in the user entries to read the user name from.
   * Defaults to 'cn'.
   */
  private String userNameEntryAttribute = "cn";

  /**
   * The objectClass of the users.
   * Defaults to 'person'.
   */
  private String userObjectClass = "person";

  /**
   * The attribute in the group entries which uniquely identifies them from the sibling entries.
   * Defaults to 'cn'.
   */
  private String uniqueGroupEntryAttribute = "cn";

  /**
   * The search base for group membership searches. Defaults to "".
   */
  private String groupSearchBase = "";

  /**
   * The objectClass of the groups.
   * Defaults to 'group'.
   */
  private String groupObjectClass = "group";

  /**
   * The attribute in the group entries to read the group name from.
   * Defaults to 'name'.
   */
  private String groupNameEntryAttribute = "name";

  public String getUrl()
  {
    return url;
  }

  public void setUrl( String url )
  {
    this.url = url;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword( String passwordLdap )
  {
    this.password = passwordLdap;
  }

  public String getUserDn()
  {
    return userDn;
  }

  public void setUserDn( String userDn )
  {
    this.userDn = userDn;
  }

  public String getGroupSearchBase()
  {
    return groupSearchBase;
  }

  public void setGroupSearchBase( String groupSearchBase )
  {
    this.groupSearchBase = groupSearchBase;
  }

  public String getBaseDn()
  {
    return baseDn;
  }

  public void setBaseDn( String baseDn )
  {
    this.baseDn = baseDn;
  }

  public String getUserDnPatterns()
  {
    return userDnPatterns;
  }

  public void setUserDnPatterns( String userDnPatterns )
  {
    this.userDnPatterns = userDnPatterns;
  }

  public String getPasswordAttribute()
  {
    return passwordAttribute;
  }

  public void setPasswordAttribute( String passwordAttribute )
  {
    this.passwordAttribute = passwordAttribute;
  }

  public String getUniqueUserEntryAttribute()
  {
    return uniqueUserEntryAttribute;
  }

  public void setUniqueUserEntryAttribute( String uniqueUserEntryAttribute )
  {
    this.uniqueUserEntryAttribute = uniqueUserEntryAttribute;
  }

  public String getUniqueGroupEntryAttribute()
  {
    return uniqueGroupEntryAttribute;
  }

  public void setUniqueGroupEntryAttribute( String uniqueGroupEntryAttribute )
  {
    this.uniqueGroupEntryAttribute = uniqueGroupEntryAttribute;
  }

  public String getGroupObjectClass()
  {
    return groupObjectClass;
  }

  public void setGroupObjectClass( String groupObjectClass )
  {
    this.groupObjectClass = groupObjectClass;
  }

  public String getGroupNameEntryAttribute()
  {
    return getUserNameEntryAttribute();
  }

  public void setGroupNameEntryAttribute( String groupNameEntryAttribute )
  {
    this.setUserNameEntryAttribute( groupNameEntryAttribute );
  }

  public String getUserObjectClass()
  {
    return userObjectClass;
  }

  public void setUserObjectClass( String userObjectClass )
  {
    this.userObjectClass = userObjectClass;
  }

  public String getUserNameEntryAttribute()
  {
    return userNameEntryAttribute;
  }

  public void setUserNameEntryAttribute( String userNameEntryAttribute )
  {
    this.userNameEntryAttribute = userNameEntryAttribute;
  }

}
