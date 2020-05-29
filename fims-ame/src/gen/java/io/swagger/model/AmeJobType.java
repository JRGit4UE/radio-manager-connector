package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.AmeJobTypeProcessed;
import io.swagger.model.AmeJobTypeProfile;
import io.swagger.model.BMObjectType;
import io.swagger.model.NotifyAtType;
import java.util.Date;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class AmeJobType   {
  
  private String resourceID = null;
  private String revisionID = null;
  private Date resourceCreationDate = null;
  private Date resourceModifiedDate = null;

  public AmeJobType() {
	  System.out.println("job constructor");
  }
  
  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    NEW("new"),

        QUEUED("queued"),

        SCHEDULED("scheduled"),

        RUNNING("running"),

        PAUSED("paused"),

        COMPLETED("completed"),

        CANCELED("canceled"),

        STOPPED("stopped"),

        FAILED("failed"),

        CLEANED("cleaned"),

        UNKNOWN("unknown");
    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }
  }

  private StatusEnum status = null;
  private BMObjectType bmObject = null;

  /**
   * NOTE currently not supported
   */
  public enum PriorityEnum {
    LOW("low"),

        MEDIUM("medium"),

        HIGH("high"),

        URGENT("urgent"),

        IMMEDIATE("immediate");
    private String value;

    PriorityEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }
  }

  private PriorityEnum priority = null;
  private Date jobStartedTime = null;
  private Date jobElapsedTime = null;
  private Date jobCompletedTime = null;
  private AmeJobTypeProcessed processed = null;
  private AmeJobTypeProfile profile = null;
  private NotifyAtType notifyAt = null;

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("resourceID")
  public String getResourceID() {
    return resourceID;
  }
  public void setResourceID(String resourceID) {
	  System.out.println("setting resource id");
    this.resourceID = resourceID;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("revisionID")
  public String getRevisionID() {
    return revisionID;
  }
  public void setRevisionID(String revisionID) {
    this.revisionID = revisionID;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("resourceCreationDate")
  public Date getResourceCreationDate() {
    return resourceCreationDate;
  }
  public void setResourceCreationDate(Date resourceCreationDate) {
    this.resourceCreationDate = resourceCreationDate;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("resourceModifiedDate")
  public Date getResourceModifiedDate() {
    return resourceModifiedDate;
  }
  public void setResourceModifiedDate(Date resourceModifiedDate) {
    this.resourceModifiedDate = resourceModifiedDate;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }
  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("bmObject")
  public BMObjectType getBmObject() {
    return bmObject;
  }
  public void setBmObject(BMObjectType bmObject) {
    this.bmObject = bmObject;
  }

  /**
   * NOTE currently not supported
   **/
  
  @ApiModelProperty(value = "NOTE currently not supported")
  @JsonProperty("priority")
  public PriorityEnum getPriority() {
    return priority;
  }
  public void setPriority(PriorityEnum priority) {
    this.priority = priority;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("jobStartedTime")
  public Date getJobStartedTime() {
    return jobStartedTime;
  }
  public void setJobStartedTime(Date jobStartedTime) {
    this.jobStartedTime = jobStartedTime;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("jobElapsedTime")
  public Date getJobElapsedTime() {
    return jobElapsedTime;
  }
  public void setJobElapsedTime(Date jobElapsedTime) {
    this.jobElapsedTime = jobElapsedTime;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("jobCompletedTime")
  public Date getJobCompletedTime() {
    return jobCompletedTime;
  }
  public void setJobCompletedTime(Date jobCompletedTime) {
    this.jobCompletedTime = jobCompletedTime;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("processed")
  public AmeJobTypeProcessed getProcessed() {
    return processed;
  }
  public void setProcessed(AmeJobTypeProcessed processed) {
    this.processed = processed;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("profile")
  public AmeJobTypeProfile getProfile() {
    return profile;
  }
  public void setProfile(AmeJobTypeProfile profile) {
    this.profile = profile;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("notifyAt")
  public NotifyAtType getNotifyAt() {
    return notifyAt;
  }
  public void setNotifyAt(NotifyAtType notifyAt) {
    this.notifyAt = notifyAt;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AmeJobType ameJobType = (AmeJobType) o;
    return Objects.equals(resourceID, ameJobType.resourceID) &&
        Objects.equals(revisionID, ameJobType.revisionID) &&
        Objects.equals(resourceCreationDate, ameJobType.resourceCreationDate) &&
        Objects.equals(resourceModifiedDate, ameJobType.resourceModifiedDate) &&
        Objects.equals(status, ameJobType.status) &&
        Objects.equals(bmObject, ameJobType.bmObject) &&
        Objects.equals(priority, ameJobType.priority) &&
        Objects.equals(jobStartedTime, ameJobType.jobStartedTime) &&
        Objects.equals(jobElapsedTime, ameJobType.jobElapsedTime) &&
        Objects.equals(jobCompletedTime, ameJobType.jobCompletedTime) &&
        Objects.equals(processed, ameJobType.processed) &&
        Objects.equals(profile, ameJobType.profile) &&
        Objects.equals(notifyAt, ameJobType.notifyAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resourceID, revisionID, resourceCreationDate, resourceModifiedDate, status, bmObject, priority, jobStartedTime, jobElapsedTime, jobCompletedTime, processed, profile, notifyAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" { \n");
    boolean isFirst = false;
    sb.append("    \"resourceID\": \"").append(toIndentedString(resourceID)).append("\"\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"revisionID\": \"").append(toIndentedString(revisionID)).append("\"\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"resourceCreationDate\": \"").append(toIndentedString(resourceCreationDate)).append("\"\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"resourceModifiedDate\": \"").append(toIndentedString(resourceModifiedDate)).append("\"\n");
    
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"status\": \"").append(toIndentedString(status)).append("\"\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"bmObject\": ").append(toIndentedString(bmObject)).append("\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"priority\": \"").append(toIndentedString(priority)).append("\"\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"jobStartedTime\": \"").append(toIndentedString(jobStartedTime)).append("\"\n");
    if (jobElapsedTime  != null) {
    	if (isFirst) isFirst=false; else sb.append(",\n"); 
    	sb.append("    \"jobElapsedTime\": \"").append(toIndentedString(jobElapsedTime)).append("\"\n");
    }
    if (jobCompletedTime != null) {
    	if (isFirst) isFirst=false; else sb.append(",\n"); 
    	sb.append("    \"jobCompletedTime\": \"").append(toIndentedString(jobCompletedTime)).append("\"\n");
    }
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"processed\": ").append(toIndentedString(processed)).append("\n");
    if (isFirst) isFirst=false; else sb.append(",\n"); 
    sb.append("    \"profile\": ").append(toIndentedString(profile)).append("\n");
    if (notifyAt != null) {
    	if (isFirst) isFirst=false; else sb.append(",\n"); 
    	sb.append("    \"notifyAt\": ").append(toIndentedString(notifyAt)).append("\n");
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

