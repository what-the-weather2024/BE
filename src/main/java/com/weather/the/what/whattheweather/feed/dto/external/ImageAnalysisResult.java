package com.weather.the.what.whattheweather.feed.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ImageAnalysisResult {
  @JsonProperty(value = "status_code")
  private String statusCode;
  @JsonProperty(value = "weather")
  private String weather;
  @JsonProperty(value = "prob")
  private Double prob;
  @JsonProperty(value = "error")
  private String error;
}
