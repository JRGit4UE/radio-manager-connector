package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class AmeItemParameterType   {
  
  private String parameterName = null;
  private String value = null;
  private String type = null;
  private String unit = null;
  private String representation = null;
  private String track = null;

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("parameterName")
  public String getParameterName() {
    return parameterName;
  }
  public void setParameterName(String parameterName) {
    this.parameterName = parameterName;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("value")
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("type")
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("unit")
  public String getUnit() {
    return unit;
  }
  public void setUnit(String unit) {
    this.unit = unit;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("representation")
  public String getRepresentation() {
    return representation;
  }
  public void setRepresentation(String representation) {
    this.representation = representation;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("track")
  public String getTrack() {
    return track;
  }
  public void setTrack(String track) {
    this.track = track;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AmeItemParameterType ameItemParameterType = (AmeItemParameterType) o;
    return Objects.equals(parameterName, ameItemParameterType.parameterName) &&
        Objects.equals(value, ameItemParameterType.value) &&
        Objects.equals(type, ameItemParameterType.type) &&
        Objects.equals(unit, ameItemParameterType.unit) &&
        Objects.equals(representation, ameItemParameterType.representation) &&
        Objects.equals(track, ameItemParameterType.track);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parameterName, value, type, unit, representation, track);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" {\n");
    boolean isFirst = false;
    sb.append("    \"parameterName\": \"").append(toIndentedString(parameterName)).append("\"\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"value\": \"").append(toIndentedString(value)).append("\"\n");
    if (type != null) {
    	if (isFirst) isFirst=false; else sb.append(",\n"); 
    	sb.append("    \"type\": \"").append(toIndentedString(type)).append("\"\n");
    }
    if (unit != null) {
	    if (isFirst) isFirst=false; else sb.append(",\n"); 
    	sb.append("    \"unit\": \"").append(toIndentedString(unit)).append("\"\n");
    }
    if (representation != null) {
	    if (isFirst) isFirst=false; else sb.append(",\n"); 
    	sb.append("    \"representation\": \"").append(toIndentedString(representation)).append("\"\n");
    }
    if (track != null) {
    	if (isFirst) isFirst=false; else sb.append(",\n"); 
   		sb.append("    \"track\": \"").append(toIndentedString(track)).append("\"\n");
    }
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

