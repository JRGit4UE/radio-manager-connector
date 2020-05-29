package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.BMContentType;
import java.util.List;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class BMObjectType   {
  
  private List<BMContentType> bmContents = new ArrayList<BMContentType>();

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("bmContents")
  public List<BMContentType> getBmContents() {
    return bmContents;
  }
  public void setBmContents(List<BMContentType> bmContents) {
    this.bmContents = bmContents;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BMObjectType bmObjectType = (BMObjectType) o;
    return Objects.equals(bmContents, bmObjectType.bmContents);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bmContents);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" {\n");

    sb.append("    \"bmContents\": ").append(toIndentedString(bmContents)).append("\n");

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

