package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AmeItemParameterType;
import java.util.List;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class AmeAtomTypeAmeItem   {
  
  private String ameItemID = null;
  private String ameItemName = null;
  private List<AmeItemParameterType> ameItemInputParameters = new ArrayList<AmeItemParameterType>();

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("ameItemID")
  public String getAmeItemID() {
    return ameItemID;
  }
  public void setAmeItemID(String ameItemID) {
    this.ameItemID = ameItemID;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("ameItemName")
  public String getAmeItemName() {
    return ameItemName;
  }
  public void setAmeItemName(String ameItemName) {
    this.ameItemName = ameItemName;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("ameItemInputParameters")
  public List<AmeItemParameterType> getAmeItemInputParameters() {
    return ameItemInputParameters;
  }
  public void setAmeItemInputParameters(List<AmeItemParameterType> ameItemInputParameters) {
    this.ameItemInputParameters = ameItemInputParameters;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AmeAtomTypeAmeItem ameAtomTypeAmeItem = (AmeAtomTypeAmeItem) o;
    return Objects.equals(ameItemID, ameAtomTypeAmeItem.ameItemID) &&
        Objects.equals(ameItemName, ameAtomTypeAmeItem.ameItemName) &&
        Objects.equals(ameItemInputParameters, ameAtomTypeAmeItem.ameItemInputParameters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ameItemID, ameItemName, ameItemInputParameters);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" {\n");
    boolean isFirst = false;
    sb.append("    \"ameItemID\": \"").append(toIndentedString(ameItemID)).append("\"\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"ameItemName\": \"").append(toIndentedString(ameItemName)).append("\"\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"ameItemInputParameters\": ").append(toIndentedString(ameItemInputParameters)).append("\n");
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

