package com.weather.the.what.whattheweather.global.http.dto.weather;

public record WeatherForecastItem(
        String baseDate,
        String baseTime,
        String category,
        String fcstDate,
        String fcstTime,
        String fcstValue,
        int nx,
        int ny
) {
}
