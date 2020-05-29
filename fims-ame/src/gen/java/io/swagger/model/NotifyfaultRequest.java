package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import io.swagger.annotations.*;

@ApiModel(description="")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class NotifyfaultRequest   {
  
  private String mediaAMEFaultNotificationType = null;

  /**
   * 
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("mediaAMEFaultNotificationType")
  public String getMediaAMEFaultNotificationType() {
    return mediaAMEFaultNotificationType;
  }
  public void setMediaAMEFaultNotificationType(String mediaAMEFaultNotificationType) {
    this.mediaAMEFaultNotificationType = mediaAMEFaultNotificationType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NotifyfaultRequest notifyfaultRequest = (NotifyfaultRequest) o;
    return Objects.equals(mediaAMEFaultNotificationType, notifyfaultRequest.mediaAMEFaultNotificationType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mediaAMEFaultNotificationType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" {\n");
    
    sb.append("    \"mediaAMEFaultNotification\": ").append(toIndentedString(mediaAMEFaultNotificationType)).append("\n");
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

