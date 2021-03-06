package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AmeAtomTypeAmeItem;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class AmeAtomType   {
  
  private AmeAtomTypeAmeItem ameItem = null;

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("AmeItem")
  public AmeAtomTypeAmeItem getAmeItem() {
    return ameItem;
  }
  public void setAmeItem(AmeAtomTypeAmeItem ameItem) {
    this.ameItem = ameItem;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AmeAtomType ameAtomType = (AmeAtomType) o;
    return Objects.equals(ameItem, ameAtomType.ameItem);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ameItem);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" {\n");
    
    sb.append("    \"ameItem\": ").append(toIndentedString(ameItem)).append("\n");
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

