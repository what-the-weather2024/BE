package com.weather.the.what.whattheweather.umbrella.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "umbrellas")
public class Umbrella {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "name")
  private String name; //대여소이름
  @Column(name = "addr")
  private String addr; //대여소주소
  @Column(name = "addr_detail")
  private String addrDetail; //대여소상세주소
  @Column(name = "zip_code")
  private String zipCode; //대여소우편번호
  @Column(name = "phone_number")
  private String phoneNumber; //대여소전화번호
  @Column(name = "detail_info")
  private String detailInfo; //대여소상세정보
  @Column(name = "latitude")
  private Double latitude; //대여소위도
  @Column(name = "longitude")
  private Double longitude; //대여소경도
}
