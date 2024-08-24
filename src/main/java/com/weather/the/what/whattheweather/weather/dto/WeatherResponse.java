package com.weather.the.what.whattheweather.weather.dto;

public record WeatherResponse(
        String weatherStatus,
        int temperature
) {

    public static WeatherResponse of(String weatherStatus, int temperature) {
        return new WeatherResponse(weatherStatus, temperature);
    }
}
