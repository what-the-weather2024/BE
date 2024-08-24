package com.weather.the.what.whattheweather.auth.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.Date;

@Getter
@RequiredArgsConstructor
public enum Token {

    ACCESS_TOKEN("Authorization", Duration.ofMinutes(30)),
    REFRESH_TOKEN("Authorization-Refresh", Duration.ofDays(7));

    private final String headerName;
    private final Duration expirationTime;

    public Date getExpirationTimeDate() {
        return new Date(System.currentTimeMillis() + this.expirationTime.toMillis());
    }
}
