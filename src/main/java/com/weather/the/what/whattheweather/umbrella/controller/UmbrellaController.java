package com.weather.the.what.whattheweather.umbrella.controller;

import com.weather.the.what.whattheweather.umbrella.dto.response.UmbrellaResponse;
import com.weather.the.what.whattheweather.umbrella.service.UmbrellaService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/umbrella")
public class UmbrellaController {

  private final UmbrellaService umbrellaService;

  @GetMapping("/health")
  public String healthController() {
    log.info("umbrella ok");
    return "ok";
  }

  @GetMapping
  public ResponseEntity<List<UmbrellaResponse>> getAllUmbrellas() {
    return ResponseEntity.ok(umbrellaService.findAllUmbrellas());
  }

  @GetMapping("/nearby")
  public ResponseEntity<List<UmbrellaResponse>> getUmbrellasNearby(@RequestParam Double latitude,
                                                                  @RequestParam Double longitude,
                                                                  @RequestParam(required = false, defaultValue = "7") @Positive(message = "반경(radius)은 0보다 커야 합니다.") Double radius) {
    return ResponseEntity.ok(umbrellaService.findUmbrellasWithinRadius(latitude, longitude, radius));
  }
}
