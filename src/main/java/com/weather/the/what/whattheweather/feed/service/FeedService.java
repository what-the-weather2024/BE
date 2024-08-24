package com.weather.the.what.whattheweather.feed.service;

import com.weather.the.what.whattheweather.feed.dto.request.FeedUploadRequest;
import com.weather.the.what.whattheweather.feed.dto.response.FeedResponse;
import com.weather.the.what.whattheweather.feed.dto.external.ImageAnalysisResult;
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
import java.util.ArrayList;
import java.util.Collections;
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
    return makeMockFeedList();
  }

  // TODO 제거
  private List<FeedResponse> makeMockFeedList() {
    List<FeedResponse> list = new ArrayList<>();

    for (int i=1; i<=50; i++) {
      FeedResponse feed = new FeedResponse();
      feed.setId((long) i);
      feed.setCity("서울시");
      feed.setDistrict("용산구");
      feed.setNeighborhood("한남동");
      feed.setWeatherStatus("맑음");
      feed.setFeedImageUrl("https://d3ocx8ysnxlhd5.cloudfront.net/052211e0-a6de-49d0-b939-a135a4e42fcf::WTW-FILE");
      feed.setMemberId("member::" + i);
      feed.setCreatedAt(LocalDateTime.now());

      list.add(feed);
    }

    return list;
  }

  /**
   * 피드 업로드
   *
   * 흐름
   * - S3 파일 업로드
   * - AI 서버에 이미지 URL 전달
   * - 분석 결과 DB 저장
   * - 분석 결과 응답
   */
  @Transactional
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
    ImageAnalysisResult result = webClientService.postDataToServer(url, Collections.emptyList(), ImageAnalysisResult.class);
    log.info("AI result : {}", result);

    // 분석 결과 DB 저장
    Feed feed = new Feed();
    feed.setCity(request.getCity());
    feed.setDistrict(request.getDistrict());
    feed.setNeighborhood(request.getNeighborhood());
    feed.setWeatherStatus(result.getWeather());
    feed.setFeedImageUrl(imageUrl);
    feed.setCreatedAt(LocalDateTime.now());
    feedRepository.save(feed);

    // 분석 결과 응답
    FeedResponse feedResponse = new FeedResponse();
    feedResponse.setId(feed.getId());
    feedResponse.setCity(request.getCity());
    feedResponse.setDistrict(request.getDistrict());
    feedResponse.setNeighborhood(request.getNeighborhood());
    feedResponse.setWeatherStatus(result.getWeather());
    feedResponse.setFeedImageUrl(imageUrl);
    feedResponse.setCreatedAt(feed.getCreatedAt());

    return feedResponse;
  }
}
