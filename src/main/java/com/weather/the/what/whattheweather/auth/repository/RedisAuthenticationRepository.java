package com.weather.the.what.whattheweather.auth.repository;

import com.weather.the.what.whattheweather.global.exception.ApplicationException;
import com.weather.the.what.whattheweather.global.exception.ApplicationExceptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class RedisAuthenticationRepository implements AuthenticationRepository {

    private static final String USER_AUTH_PREFIX = "user-";

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveToken(Long memberId, String token, Duration expirationTime) {
        final ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(USER_AUTH_PREFIX + memberId, token, expirationTime);
    }

    @Override
    public void validateToken(Long memberId, String token) {
        final ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        final String savedToken = (String) operations.get(USER_AUTH_PREFIX + memberId);
        if (!Objects.equals(savedToken, token)) {
            throw new ApplicationException(ApplicationExceptionStatus.INVALID_REQUEST);
        }
    }
}
