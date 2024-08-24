package com.weather.the.what.whattheweather.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApplicationExceptionStatus {

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "6000", "인증되지 않은 사용자입니다."),
    UNSUPPORTED_TOKEN_TYPE(HttpStatus.BAD_REQUEST, "6001", "지원하지 않는 토큰입니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "6002", "유효하지 않은 파라미터를 포함하고 있습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "6003", "유효하지 않은 요청입니다."),

    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "7000", "사용자를 찾을 수 없습니다."),
    UNSUPPORTED_SOCIAL_TYPE(HttpStatus.BAD_REQUEST, "7001", "지원하지 않는 소셜 로그인입니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10000", "서버 내부 에러가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String value;
    private final String message;
}
