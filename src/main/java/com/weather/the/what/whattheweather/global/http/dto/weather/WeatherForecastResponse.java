package com.weather.the.what.whattheweather.global.http.dto.weather;

public record WeatherForecastResponse(
        WeatherForecastHeader header,
        WeatherForecastBody body
) {
}
