package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class ResourceReferenceType   {
  
  private String resourceID = null;
  private String location = null;

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("resourceID")
  public String getResourceID() {
    return resourceID;
  }
  public void setResourceID(String resourceID) {
    this.resourceID = resourceID;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("location")
  public String getLocation() {
    return location;
  }
  public void setLocation(String location) {
    this.location = location;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResourceReferenceType resourceReferenceType = (ResourceReferenceType) o;
    return Objects.equals(resourceID, resourceReferenceType.resourceID) &&
        Objects.equals(location, resourceReferenceType.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resourceID, location);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" {\n");
    boolean isFirst = false;
    sb.append("    \"resourceID\": \"").append(toIndentedString(resourceID)).append("\"\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"location\": \"").append(toIndentedString(location)).append("\"\n");
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

