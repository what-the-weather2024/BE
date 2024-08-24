package com.weather.the.what.whattheweather.auth.dto;

import java.time.LocalDateTime;

public record TokenResponse(
        String token,
        LocalDateTime expiredTime
) {

    public static TokenResponse of(String token, LocalDateTime expiredTime) {
        return new TokenResponse(token, expiredTime);
    }
}
