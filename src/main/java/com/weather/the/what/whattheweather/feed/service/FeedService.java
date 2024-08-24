package com.weather.the.what.whattheweather.feed.service;

import com.weather.the.what.whattheweather.feed.dto.external.ImageAnalysisResult;
import com.weather.the.what.whattheweather.feed.dto.request.FeedUploadRequest;
import com.weather.the.what.whattheweather.feed.dto.response.FeedResponse;
import com.weather.the.what.whattheweather.feed.entity.Feed;
import com.weather.the.what.whattheweather.feed.repository.FeedRepository;
import com.weather.the.what.whattheweather.global.http.WebClientService;
import com.weather.the.what.whattheweather.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {

  private final S3Service s3Service;
  private final WebClientService webClientService;
  private final FeedRepository feedRepository;

  /*
   * 피드 목록 50개 조회
   *
   * 비회원 : 최근 생성된 이미지 목록
   * 회원 : 검색 조건(시/구/동)에 따른 이미지 목록
   * TODO: 페이지네이션
   */
  public List<FeedResponse> findFeeds() {
    List<Feed> feedList = feedRepository.findAll().stream()
        .sorted(Comparator.comparing(Feed::getCreatedAt).reversed())
        .toList();

    List<FeedResponse> feedResponseList = new ArrayList<>();
    feedList.forEach(feed -> {
      FeedResponse feedResponse = new FeedResponse();
      feedResponse.setId(feed.getId());
      feedResponse.setAddress(feed.getAddress());
      feedResponse.setFeedImageUrl(feed.getFeedImageUrl());
      feedResponse.setWeatherStatus(feed.getWeatherStatus());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      feedResponse.setCreatedAt(feed.getCreatedAt().format(formatter));
      feedResponse.setMemberId(feed.getMemberId());

      feedResponseList.add(feedResponse);
    });

    return feedResponseList;
  }

  // TODO 제거
//  private List<FeedResponse> makeMockFeedList() {
//    List<FeedResponse> list = new ArrayList<>();
//
//    for (int i=1; i<=50; i++) {
//      FeedResponse feed = new FeedResponse();
//      feed.setId((long) i);
//      feed.setAddress("서울시 용산구 한남동");
//      feed.setWeatherStatus("맑음");
//      feed.setFeedImageUrl("https://d3ocx8ysnxlhd5.cloudfront.net/052211e0-a6de-49d0-b939-a135a4e42fcf::WTW-FILE");
//      feed.setMemberId("member::" + i);
//      feed.setCreatedAt(LocalDateTime.now());
//
//      list.add(feed);
//    }
//
//    return list;
//  }

  /**
   * 피드 업로드

   * 흐름
   * - S3 파일 업로드
   * - AI 서버에 이미지 URL 전달
   * - 분석 결과 DB 저장
   * - 분석 결과 응답
   */
  @Transactional(rollbackFor = Exception.class)
  public FeedResponse uploadFeed(FeedUploadRequest request) {
    // S3 파일 업로드
    String imageUrl;
    try {
      imageUrl = s3Service.uploadImage(request.getFile());
    } catch (IOException ex) {
      throw new RuntimeException("업로드 실패");
    }

    // AI 서버에 이미지 URL 전달
    String url = "http://43.202.46.159:8000/wtw-ai/image_classification" + "?image_url=" + imageUrl;
    ImageAnalysisResult result = null;
    try {
      result = webClientService.postDataToServer(url, Collections.emptyList(), ImageAnalysisResult.class);
      log.info("[Feed AI result] : {}", result);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException("타임 아웃");
    }

    if (result == null) {
      log.error("이미지 분석 결과 없음");
      throw new RuntimeException("이미지 분석 결과 없음");
    }

    if ("기타".equals(result.getWeather())) {
      FeedResponse feedResponse = new FeedResponse();

      feedResponse.setWeatherStatus(result.getWeather());
      return feedResponse;
    }

    // 분석 결과 DB 저장
    Feed feed = new Feed();
    feed.setCity(request.getCity());
    feed.setDistrict(request.getDistrict());
    feed.setWeatherStatus(result.getWeather());
    feed.setFeedImageUrl(imageUrl);
    feed.setCreatedAt(LocalDateTime.now().plusHours(9));
    feedRepository.save(feed);

    // 분석 결과 응답
    FeedResponse feedResponse = new FeedResponse();
    feedResponse.setId(feed.getId());
    feedResponse.setAddress(feed.getAddress());
    feedResponse.setWeatherStatus(result.getWeather());
    feedResponse.setWeatherProb((result.getProb() * 100) + "%");
    feedResponse.setFeedImageUrl(imageUrl);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    feedResponse.setCreatedAt(feed.getCreatedAt().format(formatter));

    return feedResponse;
  }
}
