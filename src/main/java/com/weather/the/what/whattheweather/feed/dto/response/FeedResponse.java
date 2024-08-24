package com.weather.the.what.whattheweather.feed.dto.response;

import com.weather.the.what.whattheweather.feed.entity.Feed;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

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

  public static FeedResponse of(Feed feed, double prob) {
    FeedResponse feedResponse = new FeedResponse();

    feedResponse.setId(feed.getId());
    feedResponse.setAddress(feed.getAddress());
    feedResponse.setWeatherStatus(feed.getWeatherStatus());
    feedResponse.setWeatherProb((prob * 100) + "%");
    feedResponse.setFeedImageUrl(feed.getFeedImageUrl());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    feedResponse.setCreatedAt(feed.getCreatedAt().format(formatter));
    feedResponse.setMemberId(feedResponse.getMemberId());

    return feedResponse;
  }
}
