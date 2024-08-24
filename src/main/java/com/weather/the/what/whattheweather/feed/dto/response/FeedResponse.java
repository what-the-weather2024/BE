package com.weather.the.what.whattheweather.feed.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FeedResponse {
  private Long id; //피드아이디
  private String city; //시
  private String district; //구
  private String neighborhood; //동
  private String weatherStatus; //날씨상태
  private String feedImageUrl; //게시물이미지
  private LocalDateTime createdAt; //생성일자
  private String memberId; //회원아이디
}
