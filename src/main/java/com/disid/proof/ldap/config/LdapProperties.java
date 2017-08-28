package com.disid.proof.ldap.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer.PasswordCompareConfigurer;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * LDAP configuration properties
 */
@Component
@ConfigurationProperties( prefix = "application.ldap", ignoreUnknownFields = false )
@Validated
public class LdapProperties
{

  @Valid
  /**
   * LDAP connection context configuration properties.
   * @see DefaultSpringSecurityContextSource
   */
  private final Context context = new Context();

  /**
   * LDAP authentication configuration properties.
   * @see LdapAuthenticationProviderConfigurer
   */
  private final Auth auth = new Auth();

  /**
   * LDAP data synchronization configuration properties.
   */
  private final Sync sync = new Sync();

  public Context getContext()
  {
    return context;
  }

  public Auth getAuth()
  {
    return auth;
  }

  public Sync getSync()
  {
    return sync;
  }

  /**
   * LDAP context configuration properties.
   * @see DefaultSpringSecurityContextSource
   */
  public static class Context
  {
    /**
     * The URL to connect to the LDAP server.
     * @see DefaultSpringSecurityContextSource#DefaultSpringSecurityContextSource(String)
     */
    @NotNull
    private String url;

    /**
     * The user distinguished name (principal) to use for connecting to the LDAP server.
     * Defaults to "".
     * @see DefaultSpringSecurityContextSource#setUserDn(String)
     */
    private String userDn = "";

    /**
     * The password to connect to the LDAP server.
     * Defaults to "".
     * @see DefaultSpringSecurityContextSource#setPassword(String)
     */
    private String password = "";

    /**
     * The base suffix from which all operations should origin. 
     * If a base suffix is set, you will not have to (and, indeed, must not) specify the full
     * distinguished names in any operations performed.
     * Defaults to "".
     * @see DefaultSpringSecurityContextSource#setBase(String)
     */
    private String baseDn = "";

    public String getUrl()
    {
      return url;
    }

    public void setUrl( String url )
    {
      this.url = url;
    }

    public String getUserDn()
    {
      return userDn;
    }

    public void setUserDn( String userDn )
    {
      this.userDn = userDn;
    }

    public String getPassword()
    {
      return password;
    }

    public void setPassword( String password )
    {
      this.password = password;
    }

    public String getBaseDn()
    {
      return baseDn;
    }

    public void setBaseDn( String baseDn )
    {
      this.baseDn = baseDn;
    }
  }

  /**
   * LDAP authentication configuration properties.
   * @see LdapAuthenticationProviderConfigurer
   */
  public static class Auth
  {
    /**
     * If your users are at a fixed location in the directory 
     * (i.e. you can work out the DN directly from the username without doing a directory search), 
     * you can use this attribute to map directly to the DN. It maps directly to the userDnPatterns
     * property of AbstractLdapAuthenticator. The value is a specific pattern used to build the
     * user's DN, for example "uid={0},ou=people".
     * The key "{0}" must be present and will be substituted with the username.
     * Defaults to "".
     * @see LdapAuthenticationProviderConfigurer#userDnPatterns(String...)
     */
    private String userDnPatterns = "";

    /**
     * The attribute in the directory which contains the user password. Defaults to "userPassword".
     * @see PasswordCompareConfigurer#passwordAttribute(String)
     */
    private String passwordAttribute = "userPassword";

    /**
     * The search base for user searches. Defaults to "".
     * @see LdapAuthenticationProviderConfigurer#userSearchBase(String)
     */
    private String userSearchBase = "";

    /**
     * The search filter for user searches. Defaults to null.
     * @see LdapAuthenticationProviderConfigurer#userSearchFilter(String)
     */
    private String userSearchFilter = null;

    /**
     * The search base for group membership searches. Defaults to "".
     * @see LdapAuthenticationProviderConfigurer#groupSearchBase(String)
     */
    private String groupSearchBase = "";

    /**
     * The search filter for group membership searches. Defaults to "(uniqueMember={0})".
     * @see LdapAuthenticationProviderConfigurer#groupSearchFilter(String)
     */
    private String groupSearchFilter = "(uniqueMember={0})";

    /**
     * The search filter for group membership searches. Defaults to "cn".
     * @see LdapAuthenticationProviderConfigurer#groupSearchFilter(String)
     */
    private String groupRoleAttribute = "cn";

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

    public String getGroupSearchBase()
    {
      return groupSearchBase;
    }

    public void setGroupSearchBase( String groupSearchBase )
    {
      this.groupSearchBase = groupSearchBase;
    }

    public String getUserSearchBase()
    {
      return userSearchBase;
    }

    public void setUserSearchBase( String userSearchBase )
    {
      this.userSearchBase = userSearchBase;
    }

    public String getUserSearchFilter()
    {
      return userSearchFilter;
    }

    public void setUserSearchFilter( String userSearchFilter )
    {
      this.userSearchFilter = userSearchFilter;
    }

    public String getGroupSearchFilter()
    {
      return groupSearchFilter;
    }

    public void setGroupSearchFilter( String groupSearchFilter )
    {
      this.groupSearchFilter = groupSearchFilter;
    }

    public String getGroupRoleAttribute()
    {
      return groupRoleAttribute;
    }

    public void setGroupRoleAttribute( String groupRoleAttribute )
    {
      this.groupRoleAttribute = groupRoleAttribute;
    }
  }

  /**
   * LDAP data synchronization configuration properties.
   */
  public static class Sync
  {

    /**
     * LDAP user data synchronization configuration properties.
     */
    private final User user = new User();

    /**
     * LDAP group data synchronization configuration properties.
     */
    private final Group group = new Group();

    public User getUser()
    {
      return user;
    }

    public Group getGroup()
    {
      return group;
    }

    /**
     * LDAP user data synchronization configuration properties.
     */
    /**
     * @author cordin at http://www.disid.com[DISID Corporation S.L.]
     *
     */
    public static class User
    {
      /**
       * The attribute in the user entries which uniquely identifies them from the sibling entries.
       * Defaults to 'cn'.
       */
      public String idAttribute = "cn";

      /**
       * The attribute in the user entries to read the user name from.
       * Defaults to 'cn'.
       */
      public String nameAttribute = "cn";

      /**
       * The objectClass of the users.
       * Defaults to 'person'.
       */
      public String objectClass = "person";

      /**
       * The values to use to set the objectClass attribute when creating a new 
       * user entry.
       * Defaults to "top","person"
       */
      private String[] objectClassValues = new String[] { "top", "person", "organizationalPerson", "inetOrgPerson" };

      /**
       * The RDN identifier of the parent entry where to add the new created users.
       * Defaults to "ou=people".
       */
      private String baseRdn = "ou=people";

      public String getIdAttribute()
      {
        return idAttribute;
      }

      public void setIdAttribute( String uniqueAttribute )
      {
        this.idAttribute = uniqueAttribute;
      }

      public String getNameAttribute()
      {
        return nameAttribute;
      }

      public void setNameAttribute( String nameAttribute )
      {
        this.nameAttribute = nameAttribute;
      }

      public String getObjectClass()
      {
        return objectClass;
      }

      public void setObjectClass( String objectClass )
      {
        this.objectClass = objectClass;
      }

      public String[] getObjectClassValues()
      {
        return objectClassValues;
      }

      public void setObjectClassValues( String[] objectClassValues )
      {
        this.objectClassValues = objectClassValues;
      }

      public String getBaseRdn()
      {
        return baseRdn;
      }

      public void setBaseRdn( String baseRdn )
      {
        this.baseRdn = baseRdn;
      }

    }

    /**
     * LDAP group data synchronization configuration properties.
     */
    public static class Group
    {

      /**
       * The attribute in the group entries which uniquely identifies them from the sibling entries.
       * Defaults to 'cn'.
       */
      private String idAttribute = "cn";

      /**
       * The objectClass of the groups.
       * Defaults to 'group'.
       */
      private String objectClass = "group";

      /**
       * The attribute in the group entries to read the group name from.
       * Defaults to 'name'.
       */
      private String nameAttribute = "name";

      public String getIdAttribute()
      {
        return idAttribute;
      }

      public void setIdAttribute( String idAttribute )
      {
        this.idAttribute = idAttribute;
      }

      public String getObjectClass()
      {
        return objectClass;
      }

      public void setObjectClass( String objectClass )
      {
        this.objectClass = objectClass;
      }

      public String getNameAttribute()
      {
        return nameAttribute;
      }

      public void setNameAttribute( String nameAttribute )
      {
        this.nameAttribute = nameAttribute;
      }

    }
  }

}
