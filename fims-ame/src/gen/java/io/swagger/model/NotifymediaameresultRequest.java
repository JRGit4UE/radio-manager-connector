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
public class NotifymediaameresultRequest   {
  
  private String mediaAMEJobType = null;

  /**
   * 
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("mediaAMEJobType")
  public String getMediaAMEJobType() {
    return mediaAMEJobType;
  }
  public void setMediaAMEJobType(String mediaAMEJobType) {
    this.mediaAMEJobType = mediaAMEJobType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NotifymediaameresultRequest notifymediaameresultRequest = (NotifymediaameresultRequest) o;
    return Objects.equals(mediaAMEJobType, notifymediaameresultRequest.mediaAMEJobType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mediaAMEJobType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(toIndentedString(mediaAMEJobType));
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

