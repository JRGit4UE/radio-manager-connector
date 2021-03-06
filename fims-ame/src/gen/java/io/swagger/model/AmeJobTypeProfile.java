package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AmeReportType;
import io.swagger.model.AmeTemplateType;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class AmeJobTypeProfile   {
  
  private AmeTemplateType ameTemplate = null;
  private AmeReportType ameReport = null;

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("ameTemplate")
  public AmeTemplateType getAmeTemplate() {
    return ameTemplate;
  }
  public void setAmeTemplate(AmeTemplateType ameTemplate) {
    this.ameTemplate = ameTemplate;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("ameReport")
  public AmeReportType getAmeReport() {
    return ameReport;
  }
  public void setAmeReport(AmeReportType ameReport) {
    this.ameReport = ameReport;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AmeJobTypeProfile ameJobTypeProfile = (AmeJobTypeProfile) o;
    return Objects.equals(ameTemplate, ameJobTypeProfile.ameTemplate) &&
        Objects.equals(ameReport, ameJobTypeProfile.ameReport);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ameTemplate, ameReport);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" {\n");
    boolean isFirst  = false;
    sb.append("    \"ameTemplate\": ").append(toIndentedString(ameTemplate)).append("\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"ameReport\": ").append(toIndentedString(ameReport)).append("\n");
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

