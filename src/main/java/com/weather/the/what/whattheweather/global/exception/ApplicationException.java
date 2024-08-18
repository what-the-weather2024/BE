package com.weather.the.what.whattheweather.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ApplicationException extends RuntimeException {

    private final HttpStatus status;
    private final String value;

    public ApplicationException(ApplicationExceptionStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.status = exceptionStatus.getHttpStatus();
        this.value = exceptionStatus.getValue();
    }
}
