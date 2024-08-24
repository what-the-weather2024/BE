package com.weather.the.what.whattheweather.auth.token;

import java.time.LocalDateTime;

public record JwtPayload(
        Long memberId,
        LocalDateTime expiredTime
) {
}
