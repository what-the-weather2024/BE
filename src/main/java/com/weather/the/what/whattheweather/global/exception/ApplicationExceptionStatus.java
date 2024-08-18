package com.weather.the.what.whattheweather.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApplicationExceptionStatus {

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "6000", "인증되지 않은 사용자입니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10000", "서버 내부 에러가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String value;
    private final String message;
}
