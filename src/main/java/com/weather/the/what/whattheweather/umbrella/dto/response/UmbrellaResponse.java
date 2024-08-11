package com.weather.the.what.whattheweather.umbrella.dto.response;

import com.weather.the.what.whattheweather.umbrella.entity.Umbrella;
import lombok.*;

@Getter
@Setter
@Builder
public class UmbrellaResponse {
  private String name;
  private String addr;
  private String addrDetail;
  private String zipCode;
  private String phoneNumber;
  private Double latitude;
  private Double longitude;

  public static UmbrellaResponse of(Umbrella umbrella) {
    return UmbrellaResponse.builder()
        .name(umbrella.getName())
        .addr(umbrella.getAddr())
        .addrDetail(umbrella.getAddrDetail())
        .zipCode(umbrella.getZipCode())
        .phoneNumber(umbrella.getPhoneNumber())
        .latitude(umbrella.getLatitude())
        .longitude(umbrella.getLongitude())
        .build();
  }
}
