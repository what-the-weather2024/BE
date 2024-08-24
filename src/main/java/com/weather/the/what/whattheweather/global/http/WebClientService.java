package com.weather.the.what.whattheweather.global.http;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class WebClientService {

  private final WebClient.Builder webClientBuilder;

  // TODO: Get 요청 메서드 만들기

  // TODO: RequestBody null 분기처리
  public <T> T postDataToServer(String url, Object requestBody, Class<T> responseType) {
    WebClient webClient = webClientBuilder.build();

    return webClient.post()
        .uri(url)
        .bodyValue(requestBody)
        .retrieve()
        .bodyToMono(responseType)
        .timeout(Duration.ofSeconds(20))
        .block(); // 비동기 결과를 동기적으로 대기
  }
}
