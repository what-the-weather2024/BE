package com.weather.the.what.whattheweather.feed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "feeds")
public class Feed {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "city")
  private String city; //시
  @Column(name = "district")
  private String district; //구
  @Column(name = "weather_status")
  private String weatherStatus; //날씨
  @Column(name = "feed_image_url")
  private String feedImageUrl; //피드이미지
  @Column(name = "created_at")
  private LocalDateTime createdAt; //생성일자
  // TODO 회원테이블 관계 매핑
  @Column(name = "member_id")
  private String memberId;

  public String getAddress() {
    return (city == null ? "" : city) + (district == null ? "" : district);
  }
}
