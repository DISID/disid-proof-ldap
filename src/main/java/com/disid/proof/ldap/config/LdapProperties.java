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
   * url
   */
  private String url;

  /**
   * LDAP password.
   * IMPORTANT: default value must not be null
   */
  private String passwordLdap = "";

  /**
   * user Dn
   * IMPORTANT: default value must not be null
   */
  private String userDn = "";

  /**
   * group search base
   * IMPORTANT: default value must not be null
   */
  private String groupSearchBase = "";

  /**
   * domain
   * IMPORTANT: default value must not be null
   */
  private String domain = "";

  public String getUrl()
  {
    return url;
  }

  public void setUrl( String url )
  {
    this.url = url;
  }

  public String getPasswordLdap()
  {
    return passwordLdap;
  }

  public void setPasswordLdap( String passwordLdap )
  {
    this.passwordLdap = passwordLdap;
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

  public String getDomain()
  {
    return domain;
  }

  public void setDomain( String domain )
  {
    this.domain = domain;
  }

}
