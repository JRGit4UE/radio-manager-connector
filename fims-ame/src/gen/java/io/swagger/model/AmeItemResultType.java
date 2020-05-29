package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.AmeItemParameterType;
import io.swagger.model.ToolInformationType;
import java.util.List;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class AmeItemResultType   {

	
	  private String ameItemID = null;
	  private String ameItemName = null;

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
  private String resultDescription = null;
  private ToolInformationType toolInformation = null;


  /**
   * Gets or Sets detectionMethod
   */
  public enum DetectionMethodEnum {
    AUTOMATIC("automatic"),

        HUMAN("human"),

        ASSISTED("assisted");
    private String value;

    DetectionMethodEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }
  }

  private DetectionMethodEnum detectionMethod = null;
  private List<AmeItemParameterType> ameItemOutputs = new ArrayList<AmeItemParameterType>();

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("resultDescription")
  public String getResultDescription() {
    return resultDescription;
  }
  public void setResultDescription(String resultDescription) {
    this.resultDescription = resultDescription;
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
  @JsonProperty("detectionMethod")
  public DetectionMethodEnum getDetectionMethod() {
    return detectionMethod;
  }
  public void setDetectionMethod(DetectionMethodEnum detectionMethod) {
    this.detectionMethod = detectionMethod;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("ameItemOutputs")
  public List<AmeItemParameterType> getAmeItemOutputs() {
    return ameItemOutputs;
  }
  public void setAmeItemOutputs(List<AmeItemParameterType> ameItemOutputs) {
    this.ameItemOutputs = ameItemOutputs;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AmeItemResultType ameItemResultType = (AmeItemResultType) o;
    return Objects.equals(ameItemID, ameItemResultType.ameItemID) &&
    	Objects.equals(ameItemName, ameItemResultType.ameItemName) &&
    	Objects.equals(resultDescription, ameItemResultType.resultDescription) &&
        Objects.equals(toolInformation, ameItemResultType.toolInformation) &&
        Objects.equals(detectionMethod, ameItemResultType.detectionMethod) &&
        Objects.equals(ameItemOutputs, ameItemResultType.ameItemOutputs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ameItemID, ameItemName, resultDescription, toolInformation, detectionMethod, ameItemOutputs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" {\n");
    boolean isFirst = false;
    sb.append("    \"ameItemID\": \"").append(toIndentedString(ameItemID)).append("\"\n");
    if (ameItemName != null) {
    	if (isFirst) isFirst=false; else sb.append(",\n"); 
    	sb.append("    \"ameItemName\": \"").append(toIndentedString(ameItemName)).append("\"\n");
    }
    if (resultDescription != null) {
    	if (isFirst) isFirst=false; else sb.append(",\n"); 
    	sb.append("    \"resultDescription\": \"").append(toIndentedString(resultDescription)).append("\"\n");
    }
    if (toolInformation != null) {
    	if (isFirst) isFirst=false; else sb.append(",\n"); 
    	sb.append("    \"toolInformation\": ").append(toIndentedString(toolInformation)).append("\n");
    }
    if (detectionMethod != null) {
    	if (isFirst) isFirst=false; else sb.append(",\n"); 
    	sb.append("    \"detectionMethod\": \"").append(toIndentedString(detectionMethod)).append("\"\n");
    }
    if (ameItemOutputs != null) {
    	if (isFirst) isFirst=false; else sb.append(",\n"); 
    	sb.append("    \"ameItemOutputs\": ").append(toIndentedString(ameItemOutputs)).append("\n");
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

