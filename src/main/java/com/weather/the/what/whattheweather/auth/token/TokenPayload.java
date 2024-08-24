package com.weather.the.what.whattheweather.auth.token;

import com.weather.the.what.whattheweather.auth.dto.TokenResponse;

public record TokenPayload(
        TokenResponse accessToken,
        TokenResponse refreshToken
) {
}
