package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Date;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class NotifyAtType   {
  
  private String replyTo = null;
  private String faultTo = null;


  public NotifyAtType() {
	  System.out.println("notifyAt constructor");
  }
 

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("replyTo")
  public String getReplyTo() {
    return replyTo;
  }
  public void setReplyTo(String replyTo) {
    this.replyTo = replyTo;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("faultTo")
  public String getFaultTo() {
    return faultTo;
  }
  public void setFaultTo(String faultTo) {
    this.faultTo = faultTo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NotifyAtType notifyAt = (NotifyAtType) o;
    return Objects.equals(replyTo, notifyAt.replyTo) &&
        Objects.equals(faultTo, notifyAt.faultTo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(replyTo, faultTo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" { \n");
    boolean isFirst = false;
    sb.append("    \"replyTo\": \"").append(toIndentedString(replyTo)).append("\"\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"faultTo\": \"").append(toIndentedString(faultTo)).append("\"\n");
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

