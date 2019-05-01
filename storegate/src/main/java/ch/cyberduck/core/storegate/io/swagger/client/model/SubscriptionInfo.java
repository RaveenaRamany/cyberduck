/*
 * Storegate.Web
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v4
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package ch.cyberduck.core.storegate.io.swagger.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;

/**
 * Subscription information
 */
@ApiModel(description = "Subscription information")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-04-02T17:31:35.366+02:00")
public class SubscriptionInfo {
  @JsonProperty("isTrial")
  private Boolean isTrial = null;

  @JsonProperty("expires")
  private DateTime expires = null;

  @JsonProperty("hasPaymentInfo")
  private Boolean hasPaymentInfo = null;

  public SubscriptionInfo isTrial(Boolean isTrial) {
    this.isTrial = isTrial;
    return this;
  }

   /**
   * Is the account trial
   * @return isTrial
  **/
  @ApiModelProperty(value = "Is the account trial")
  public Boolean isIsTrial() {
    return isTrial;
  }

  public void setIsTrial(Boolean isTrial) {
    this.isTrial = isTrial;
  }

  public SubscriptionInfo expires(DateTime expires) {
    this.expires = expires;
    return this;
  }

   /**
   * When the account expires (0001-01-01T00:00:00Z indicates that no expire date is set)
   * @return expires
  **/
  @ApiModelProperty(value = "When the account expires (0001-01-01T00:00:00Z indicates that no expire date is set)")
  public DateTime getExpires() {
    return expires;
  }

  public void setExpires(DateTime expires) {
    this.expires = expires;
  }

  public SubscriptionInfo hasPaymentInfo(Boolean hasPaymentInfo) {
    this.hasPaymentInfo = hasPaymentInfo;
    return this;
  }

   /**
   * 
   * @return hasPaymentInfo
  **/
  @ApiModelProperty(value = "")
  public Boolean isHasPaymentInfo() {
    return hasPaymentInfo;
  }

  public void setHasPaymentInfo(Boolean hasPaymentInfo) {
    this.hasPaymentInfo = hasPaymentInfo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SubscriptionInfo subscriptionInfo = (SubscriptionInfo) o;
    return Objects.equals(this.isTrial, subscriptionInfo.isTrial) &&
        Objects.equals(this.expires, subscriptionInfo.expires) &&
        Objects.equals(this.hasPaymentInfo, subscriptionInfo.hasPaymentInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isTrial, expires, hasPaymentInfo);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubscriptionInfo {\n");
    
    sb.append("    isTrial: ").append(toIndentedString(isTrial)).append("\n");
    sb.append("    expires: ").append(toIndentedString(expires)).append("\n");
    sb.append("    hasPaymentInfo: ").append(toIndentedString(hasPaymentInfo)).append("\n");
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

