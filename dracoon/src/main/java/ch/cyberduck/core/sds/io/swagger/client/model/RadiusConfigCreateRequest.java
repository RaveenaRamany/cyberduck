/*
 * DRACOON API
 * REST Web Services for DRACOON<br>built at: 2020-10-14 12:14:23<br><br>This page provides an overview of all available and documented DRACOON APIs, which are grouped by tags.<br>Each tag provides a collection of APIs that are intended for a specific area of the DRACOON.<br><br><a title='Developer Information' href='https://developer.dracoon.com'>Developer Information</a>&emsp;&emsp;<a title='Get SDKs on GitHub' href='https://github.com/dracoon'>Get SDKs on GitHub</a><br><br><a title='Terms of service' href='https://www.dracoon.com/terms/general-terms-and-conditions/'>Terms of service</a>
 *
 * OpenAPI spec version: 4.24.0-SNAPSHOT
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package ch.cyberduck.core.sds.io.swagger.client.model;

import java.util.Objects;
import java.util.Arrays;
import ch.cyberduck.core.sds.io.swagger.client.model.FailoverServer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * Request model for creating a RADIUS configuration
 */
@Schema(description = "Request model for creating a RADIUS configuration")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2020-10-15T15:35:23.522373+02:00[Europe/Zurich]")
public class RadiusConfigCreateRequest {
  @JsonProperty("ipAddress")
  private String ipAddress = null;

  @JsonProperty("port")
  private Integer port = null;

  @JsonProperty("sharedSecret")
  private String sharedSecret = null;

  @JsonProperty("otpPinFirst")
  private Boolean otpPinFirst = true;

  @JsonProperty("failoverServer")
  private FailoverServer failoverServer = null;

  public RadiusConfigCreateRequest ipAddress(String ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

   /**
   * RADIUS Server IP Address
   * @return ipAddress
  **/
  @Schema(required = true, description = "RADIUS Server IP Address")
  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public RadiusConfigCreateRequest port(Integer port) {
    this.port = port;
    return this;
  }

   /**
   * RADIUS Server Port
   * @return port
  **/
  @Schema(required = true, description = "RADIUS Server Port")
  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public RadiusConfigCreateRequest sharedSecret(String sharedSecret) {
    this.sharedSecret = sharedSecret;
    return this;
  }

   /**
   * Shared Secret to access the RADIUS server
   * @return sharedSecret
  **/
  @Schema(required = true, description = "Shared Secret to access the RADIUS server")
  public String getSharedSecret() {
    return sharedSecret;
  }

  public void setSharedSecret(String sharedSecret) {
    this.sharedSecret = sharedSecret;
  }

  public RadiusConfigCreateRequest otpPinFirst(Boolean otpPinFirst) {
    this.otpPinFirst = otpPinFirst;
    return this;
  }

   /**
   * Sequence order of concatenated PIN and one-time token
   * @return otpPinFirst
  **/
  @Schema(description = "Sequence order of concatenated PIN and one-time token")
  public Boolean isOtpPinFirst() {
    return otpPinFirst;
  }

  public void setOtpPinFirst(Boolean otpPinFirst) {
    this.otpPinFirst = otpPinFirst;
  }

  public RadiusConfigCreateRequest failoverServer(FailoverServer failoverServer) {
    this.failoverServer = failoverServer;
    return this;
  }

   /**
   * Get failoverServer
   * @return failoverServer
  **/
  @Schema(description = "")
  public FailoverServer getFailoverServer() {
    return failoverServer;
  }

  public void setFailoverServer(FailoverServer failoverServer) {
    this.failoverServer = failoverServer;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RadiusConfigCreateRequest radiusConfigCreateRequest = (RadiusConfigCreateRequest) o;
    return Objects.equals(this.ipAddress, radiusConfigCreateRequest.ipAddress) &&
        Objects.equals(this.port, radiusConfigCreateRequest.port) &&
        Objects.equals(this.sharedSecret, radiusConfigCreateRequest.sharedSecret) &&
        Objects.equals(this.otpPinFirst, radiusConfigCreateRequest.otpPinFirst) &&
        Objects.equals(this.failoverServer, radiusConfigCreateRequest.failoverServer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ipAddress, port, sharedSecret, otpPinFirst, failoverServer);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RadiusConfigCreateRequest {\n");
    
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
    sb.append("    port: ").append(toIndentedString(port)).append("\n");
    sb.append("    sharedSecret: ").append(toIndentedString(sharedSecret)).append("\n");
    sb.append("    otpPinFirst: ").append(toIndentedString(otpPinFirst)).append("\n");
    sb.append("    failoverServer: ").append(toIndentedString(failoverServer)).append("\n");
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
