package com.weather.the.what.whattheweather.weather.controller;

import com.weather.the.what.whattheweather.weather.dto.WeatherResponse;
import com.weather.the.what.whattheweather.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/weathers")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    public ResponseEntity<WeatherResponse> getWeatherForecast(
            @RequestParam String si,
            @RequestParam String gu
    ) {
        return ResponseEntity
                .ok()
                .body(weatherService.getWeatherForecast(si, gu));
    }
}
