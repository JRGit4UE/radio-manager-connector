package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class AmeItemFragmentParameterTypeLocator   {
  
  private BigDecimal start = null;
  private BigDecimal end = null;
  private String spatialRegion = null;

  /**
   * milliseconds
   **/
  
  @ApiModelProperty(value = "milliseconds")
  @JsonProperty("start")
  public BigDecimal getStart() {
    return start;
  }
  public void setStart(BigDecimal start) {
    this.start = start;
  }

  /**
   * milliseconds
   **/
  
  @ApiModelProperty(value = "milliseconds")
  @JsonProperty("end")
  public BigDecimal getEnd() {
    return end;
  }
  public void setEnd(BigDecimal end) {
    this.end = end;
  }

  /**
   * W3C Media Fragment syntax
   **/
  
  @ApiModelProperty(value = "W3C Media Fragment syntax")
  @JsonProperty("spatialRegion")
  public String getSpatialRegion() {
    return spatialRegion;
  }
  public void setSpatialRegion(String spatialRegion) {
    this.spatialRegion = spatialRegion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AmeItemFragmentParameterTypeLocator ameItemFragmentParameterTypeLocator = (AmeItemFragmentParameterTypeLocator) o;
    return Objects.equals(start, ameItemFragmentParameterTypeLocator.start) &&
        Objects.equals(end, ameItemFragmentParameterTypeLocator.end) &&
        Objects.equals(spatialRegion, ameItemFragmentParameterTypeLocator.spatialRegion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, end, spatialRegion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" {\n");
    boolean isFirst = false;
    sb.append("    \"start\": \"").append(toIndentedString(start)).append("\"\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"end\": \"").append(toIndentedString(end)).append("\"\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"spatialRegion\": \"").append(toIndentedString(spatialRegion)).append("\"\n");
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

