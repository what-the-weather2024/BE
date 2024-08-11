package com.weather.the.what.whattheweather.umbrella.service;

import com.weather.the.what.whattheweather.umbrella.dto.response.UmbrellaResponse;
import com.weather.the.what.whattheweather.umbrella.repository.UmbrellaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UmbrellaService {

  private final UmbrellaRepository umbrellaRepository;

  public List<UmbrellaResponse> findAllUmbrellas() {
    return umbrellaRepository.findAll().stream()
        .map(UmbrellaResponse::of)
        .collect(Collectors.toList());
  }

  public List<UmbrellaResponse> findUmbrellasWithinRadius(Double latitude, Double longitude, Double radius) {
    return umbrellaRepository.findAllWithinRadius(latitude, longitude, radius).stream()
        .map(UmbrellaResponse::of)
        .collect(Collectors.toList());
  }

}
