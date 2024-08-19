package com.weather.the.what.whattheweather.feed.controller;

import com.weather.the.what.whattheweather.feed.dto.request.FeedUploadRequest;
import com.weather.the.what.whattheweather.feed.dto.response.FeedResponse;
import com.weather.the.what.whattheweather.feed.dto.external.ImageAnalysisResult;
import com.weather.the.what.whattheweather.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
public class FeedController {

  private final FeedService feedService;

  @GetMapping("/health")
  public String healthController() {
    log.info("feed ok");
    return "ok";
  }

  /*
   * 피드 목록 조회
  */
  @GetMapping
  public ResponseEntity<List<FeedResponse>> getFeeds() {
    return ResponseEntity.ok(feedService.findFeeds());
  }

  /*
   * 피드 업로드
   *
   * request : 시, 구, 동, 이미지 파일, 회원아이디
   * response : AI 분석 결과
   */
  @PostMapping
  public ResponseEntity<?> uploadFeed(FeedUploadRequest request) {
    log.info("[Feed Upload Request] : {}", request);
    ImageAnalysisResult imageAnalysisResult = feedService.uploadFeed(request);
    return ResponseEntity.ok(imageAnalysisResult);
  }

}
