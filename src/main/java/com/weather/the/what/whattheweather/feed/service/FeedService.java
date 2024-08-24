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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {

  private static final String IMAGE_ANALYSIS_URL = "http://43.202.46.159:8000/wtw-ai/image_classification";
  private final S3Service s3Service;
  private final WebClientService webClientService;
  private final FeedRepository feedRepository;

  /*
   * 피드 목록 조회
   *
   * 비회원 : 최근 생성된 이미지 목록
   * 회원 : 검색 조건(시/구)에 따른 이미지 목록
   */
  public List<FeedResponse> findFeeds(String city, String district, int size) {
    List<Feed> feedList = null;

    if (!city.isEmpty() && district.isEmpty()){ // 시
      feedList = feedRepository.findAllByCityContainsIgnoreCase(city).stream()
          .sorted(Comparator.comparing(Feed::getCreatedAt).reversed())
          .toList();
    } else if (!city.isEmpty()) {
      feedList = feedRepository.findAllByCityContainsIgnoreCaseAndDistrictContainingIgnoreCase(city, district).stream()
          .sorted(Comparator.comparing(Feed::getCreatedAt).reversed())
          .toList();
    } else {
      feedList = feedRepository.findAll().stream()
          .sorted(Comparator.comparing(Feed::getCreatedAt).reversed())
          .toList();
    }

    return feedList.stream().map(feed -> FeedResponse.of(feed, 0.9)).collect(Collectors.toList());
  }

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
    String url = IMAGE_ANALYSIS_URL + "?image_url=" + imageUrl;
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
    return FeedResponse.of(feed, result.getProb());
  }
}
