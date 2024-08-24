package com.weather.the.what.whattheweather.auth.token;

import java.time.LocalDateTime;

public interface TokenExtractor {

    LocalDateTime getExpiredTime(String token);

    Long getMemberId(String token);
}
