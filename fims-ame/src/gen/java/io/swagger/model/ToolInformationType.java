package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class ToolInformationType   {
  
  private String name = null;
  private String vendor = null;
  private String version = null;
  private String url = null;
  private String toolID = null;

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("vendor")
  public String getVendor() {
    return vendor;
  }
  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("version")
  public String getVersion() {
    return version;
  }
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("url")
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("toolID")
  public String getToolID() {
    return toolID;
  }
  public void setToolID(String toolID) {
    this.toolID = toolID;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ToolInformationType toolInformationType = (ToolInformationType) o;
    return Objects.equals(name, toolInformationType.name) &&
        Objects.equals(vendor, toolInformationType.vendor) &&
        Objects.equals(version, toolInformationType.version) &&
        Objects.equals(url, toolInformationType.url) &&
        Objects.equals(toolID, toolInformationType.toolID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, vendor, version, url, toolID);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" {\n");
    boolean isFirst = true;
    if (name != null) {
    	if (isFirst) isFirst=false; else sb.append(",\n"); 
    	sb.append("    \"name\": \"").append(toIndentedString(name)).append("\"\n");
    }
    if (vendor != null) {
    	if (isFirst) isFirst=false; else sb.append(",\n"); 
    	sb.append("    \"vendor\": \"").append(toIndentedString(vendor)).append("\"\n");
    }
    if (version != null) {
	    if (isFirst) isFirst=false; else sb.append(",\n"); 
    	sb.append("    \"version\": \"").append(toIndentedString(version)).append("\"\n");
    }
    if (url != null) {
	    if (isFirst) isFirst=false; else sb.append(",\n"); 
	    sb.append("    \"url\": \"").append(toIndentedString(url)).append("\"\n");
    }
    if (toolID != null) {
	    if (isFirst) isFirst=false; else sb.append(",\n"); 
    	sb.append("    \"toolID\": \"").append(toIndentedString(toolID)).append("\"\n");
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

