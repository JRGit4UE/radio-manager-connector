package io.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-06-06T10:05:32.593+02:00")
public class AmeJobTypeProcessed   {
  
  private BigDecimal percentageProcessedCompleted = null;

  /**
   * minimum: 0
   * maximum: 100
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("percentageProcessedCompleted")
 @DecimalMin("0") @DecimalMax("100")  public BigDecimal getPercentageProcessedCompleted() {
    return percentageProcessedCompleted;
  }
  public void setPercentageProcessedCompleted(BigDecimal percentageProcessedCompleted) {
    this.percentageProcessedCompleted = percentageProcessedCompleted;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AmeJobTypeProcessed ameJobTypeProcessed = (AmeJobTypeProcessed) o;
    return Objects.equals(percentageProcessedCompleted, ameJobTypeProcessed.percentageProcessedCompleted);
  }

  @Override
  public int hashCode() {
    return Objects.hash(percentageProcessedCompleted);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" {\n");
    
    sb.append("    \"percentageProcessedCompleted\": ").append(toIndentedString(percentageProcessedCompleted)).append("\n");
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

