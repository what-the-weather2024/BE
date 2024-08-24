package com.weather.the.what.whattheweather.auth.dto;

import com.weather.the.what.whattheweather.auth.token.TokenPayload;

public record AuthResponse(
        TokenResponse accessToken,
        TokenResponse refreshToken
) {
    public static AuthResponse from(TokenPayload tokenPayload) {
        return new AuthResponse(
                tokenPayload.accessToken(),
                tokenPayload.refreshToken()
        );
    }
}
