package com.weather.the.what.whattheweather.feed.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedResponse {
  private Long id; //피드아이디
  private String address; //주소
  private String weatherStatus; //날씨상태
  private String weatherProb; //날씨확률
  private String feedImageUrl; //게시물이미지
  private String createdAt; //생성일자
  private String memberId; //회원아이디
}
