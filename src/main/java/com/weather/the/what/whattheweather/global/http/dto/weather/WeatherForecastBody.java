package com.weather.the.what.whattheweather.global.http.dto.weather;

public record WeatherForecastBody(
        String dataType,
        WeatherForecastItems items,
        int pageNo,
        int numOfRows,
        int totalCount
) {
}
