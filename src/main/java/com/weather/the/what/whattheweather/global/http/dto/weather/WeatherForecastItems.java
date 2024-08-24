package com.weather.the.what.whattheweather.global.http.dto.weather;

import java.util.List;

public record WeatherForecastItems(
        List<WeatherForecastItem> item
) {
}
