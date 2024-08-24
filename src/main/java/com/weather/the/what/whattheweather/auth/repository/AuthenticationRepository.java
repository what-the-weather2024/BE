package com.weather.the.what.whattheweather.auth.repository;

import java.time.Duration;

public interface AuthenticationRepository {

    void saveToken(Long memberId, String token, Duration expirationTime);

    void validateToken(Long memberId, String token);
}
