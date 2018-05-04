/*
 * DRACOON
 * Version 4.4.0 - built at: 2017-12-04 04:14:43, API server: https://demo.dracoon.com/api/v4
 *
 * OpenAPI spec version: 4.4.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package ch.cyberduck.core.sds.io.swagger.client.model;

import java.util.Objects;
import ch.cyberduck.core.sds.io.swagger.client.model.UserAuthMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * FirstAdminUser
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-05-03T10:55:56.129+02:00")
public class FirstAdminUser {
  @JsonProperty("login")
  private String login = null;

  @JsonProperty("needsToChangeUserName")
  private Boolean needsToChangeUserName = null;

  @JsonProperty("password")
  private String password = null;

  @JsonProperty("title")
  private String title = null;

  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("lastName")
  private String lastName = null;

  /**
   * Gender key, one of [m|f|n]
   */
  public enum GenderEnum {
    M("m"),
    
    F("f"),
    
    N("n");

    private String value;

    GenderEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static GenderEnum fromValue(String text) {
      for (GenderEnum b : GenderEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("gender")
  private GenderEnum gender = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("language")
  private String language = null;

  @JsonProperty("notifyUser")
  private Boolean notifyUser = null;

  @JsonProperty("authMethods")
  private List<UserAuthMethod> authMethods = null;

  public FirstAdminUser login(String login) {
    this.login = login;
    return this;
  }

   /**
   * Login name
   * @return login
  **/
  @ApiModelProperty(required = true, value = "Login name")
  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public FirstAdminUser needsToChangeUserName(Boolean needsToChangeUserName) {
    this.needsToChangeUserName = needsToChangeUserName;
    return this;
  }

   /**
   * If true, the user must change the userName at the first login
   * @return needsToChangeUserName
  **/
  @ApiModelProperty(value = "If true, the user must change the userName at the first login")
  public Boolean getNeedsToChangeUserName() {
    return needsToChangeUserName;
  }

  public void setNeedsToChangeUserName(Boolean needsToChangeUserName) {
    this.needsToChangeUserName = needsToChangeUserName;
  }

  public FirstAdminUser password(String password) {
    this.password = password;
    return this;
  }

   /**
   * An initial password may be preset
   * @return password
  **/
  @ApiModelProperty(value = "An initial password may be preset")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public FirstAdminUser title(String title) {
    this.title = title;
    return this;
  }

   /**
   * Job title
   * @return title
  **/
  @ApiModelProperty(value = "Job title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public FirstAdminUser firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

   /**
   * First name
   * @return firstName
  **/
  @ApiModelProperty(required = true, value = "First name")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public FirstAdminUser lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

   /**
   * Last name
   * @return lastName
  **/
  @ApiModelProperty(required = true, value = "Last name")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public FirstAdminUser gender(GenderEnum gender) {
    this.gender = gender;
    return this;
  }

   /**
   * Gender key, one of [m|f|n]
   * @return gender
  **/
  @ApiModelProperty(value = "Gender key, one of [m|f|n]")
  public GenderEnum getGender() {
    return gender;
  }

  public void setGender(GenderEnum gender) {
    this.gender = gender;
  }

  public FirstAdminUser email(String email) {
    this.email = email;
    return this;
  }

   /**
   * Email [NOT USED]
   * @return email
  **/
  @ApiModelProperty(value = "Email [NOT USED]")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public FirstAdminUser language(String language) {
    this.language = language;
    return this;
  }

   /**
   * Language ID or ISO 639-1 code
   * @return language
  **/
  @ApiModelProperty(value = "Language ID or ISO 639-1 code")
  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public FirstAdminUser notifyUser(Boolean notifyUser) {
    this.notifyUser = notifyUser;
    return this;
  }

   /**
   * Notify user about his new account. (default: true)
   * @return notifyUser
  **/
  @ApiModelProperty(value = "Notify user about his new account. (default: true)")
  public Boolean getNotifyUser() {
    return notifyUser;
  }

  public void setNotifyUser(Boolean notifyUser) {
    this.notifyUser = notifyUser;
  }

  public FirstAdminUser authMethods(List<UserAuthMethod> authMethods) {
    this.authMethods = authMethods;
    return this;
  }

  public FirstAdminUser addAuthMethodsItem(UserAuthMethod authMethodsItem) {
    if (this.authMethods == null) {
      this.authMethods = new ArrayList<UserAuthMethod>();
    }
    this.authMethods.add(authMethodsItem);
    return this;
  }

   /**
   * Authentication methods
   * @return authMethods
  **/
  @ApiModelProperty(value = "Authentication methods")
  public List<UserAuthMethod> getAuthMethods() {
    return authMethods;
  }

  public void setAuthMethods(List<UserAuthMethod> authMethods) {
    this.authMethods = authMethods;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FirstAdminUser firstAdminUser = (FirstAdminUser) o;
    return Objects.equals(this.login, firstAdminUser.login) &&
        Objects.equals(this.needsToChangeUserName, firstAdminUser.needsToChangeUserName) &&
        Objects.equals(this.password, firstAdminUser.password) &&
        Objects.equals(this.title, firstAdminUser.title) &&
        Objects.equals(this.firstName, firstAdminUser.firstName) &&
        Objects.equals(this.lastName, firstAdminUser.lastName) &&
        Objects.equals(this.gender, firstAdminUser.gender) &&
        Objects.equals(this.email, firstAdminUser.email) &&
        Objects.equals(this.language, firstAdminUser.language) &&
        Objects.equals(this.notifyUser, firstAdminUser.notifyUser) &&
        Objects.equals(this.authMethods, firstAdminUser.authMethods);
  }

  @Override
  public int hashCode() {
    return Objects.hash(login, needsToChangeUserName, password, title, firstName, lastName, gender, email, language, notifyUser, authMethods);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FirstAdminUser {\n");
    
    sb.append("    login: ").append(toIndentedString(login)).append("\n");
    sb.append("    needsToChangeUserName: ").append(toIndentedString(needsToChangeUserName)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    notifyUser: ").append(toIndentedString(notifyUser)).append("\n");
    sb.append("    authMethods: ").append(toIndentedString(authMethods)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

