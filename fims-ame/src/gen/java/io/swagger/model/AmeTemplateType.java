package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AmeAtomType;
import java.util.List;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class AmeTemplateType   {
  
  private List<AmeAtomType> ameAtom = new ArrayList<AmeAtomType>();
  private String ameTemplateID = null;
  private String ameTemplateName = null;

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("ameAtom")
  public List<AmeAtomType> getAmeAtom() {
    return ameAtom;
  }
  public void setAmeAtom(List<AmeAtomType> ameAtom) {
    this.ameAtom = ameAtom;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("ameTemplateID")
  public String getAmeTemplateID() {
    return ameTemplateID;
  }
  public void setAmeTemplateID(String ameTemplateID) {
    this.ameTemplateID = ameTemplateID;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("ameTemplateName")
  public String getAmeTemplateName() {
    return ameTemplateName;
  }
  public void setAmeTemplateName(String ameTemplateName) {
    this.ameTemplateName = ameTemplateName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AmeTemplateType ameTemplateType = (AmeTemplateType) o;
    return Objects.equals(ameAtom, ameTemplateType.ameAtom) &&
        Objects.equals(ameTemplateID, ameTemplateType.ameTemplateID) &&
        Objects.equals(ameTemplateName, ameTemplateType.ameTemplateName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ameAtom, ameTemplateID, ameTemplateName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" {\n");
    boolean isFirst = false;
    sb.append("    \"ameAtom\": ").append(toIndentedString(ameAtom)).append("\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"ameTemplateID\": \"").append(toIndentedString(ameTemplateID)).append("\"\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"ameTemplateName\": \"").append(toIndentedString(ameTemplateName)).append("\"\n");
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

