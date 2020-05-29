package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AmeItemResultType;
import io.swagger.model.ResourceReferenceType;
import io.swagger.model.ToolInformationType;
import java.util.List;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class AmeReportType   {
  
  private ResourceReferenceType bmContentReference = null;
  private ToolInformationType toolInformation = null;
  private String overallAnalysisDuration = null;
  private List<AmeItemResultType> ameItemResult = new ArrayList<AmeItemResultType>();

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("bmContentReference")
  public ResourceReferenceType getBmContentReference() {
    return bmContentReference;
  }
  public void setBmContentReference(ResourceReferenceType bmContentReference) {
    this.bmContentReference = bmContentReference;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("toolInformation")
  public ToolInformationType getToolInformation() {
    return toolInformation;
  }
  public void setToolInformation(ToolInformationType toolInformation) {
    this.toolInformation = toolInformation;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("overallAnalysisDuration")
  public String getOverallAnalysisDuration() {
    return overallAnalysisDuration;
  }
  public void setOverallAnalysisDuration(String overallAnalysisDuration) {
    this.overallAnalysisDuration = overallAnalysisDuration;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("ameItemResult")
  public List<AmeItemResultType> getAmeItemResult() {
    return ameItemResult;
  }
  public void setAmeItemResult(List<AmeItemResultType> ameItemResult) {
    this.ameItemResult = ameItemResult;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AmeReportType ameReportType = (AmeReportType) o;
    return Objects.equals(bmContentReference, ameReportType.bmContentReference) &&
        Objects.equals(toolInformation, ameReportType.toolInformation) &&
        Objects.equals(overallAnalysisDuration, ameReportType.overallAnalysisDuration) &&
        Objects.equals(ameItemResult, ameReportType.ameItemResult);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bmContentReference, toolInformation, overallAnalysisDuration, ameItemResult);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" {\n");
    boolean isFirst= false;
    sb.append("    \"bmContentReference\": ").append(toIndentedString(bmContentReference)).append("\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"toolInformation\": ").append(toIndentedString(toolInformation)).append("\n");
    if (overallAnalysisDuration != null) {
    	if (isFirst) isFirst=false; else sb.append(",\n"); 
    	sb.append("    \"overallAnalysisDuration\": ").append(toIndentedString(overallAnalysisDuration)).append("\n");
    }
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"ameItemResult\": ").append(toIndentedString(ameItemResult)).append("\n");
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

