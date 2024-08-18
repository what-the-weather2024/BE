package com.weather.the.what.whattheweather.global.dto;

import com.weather.the.what.whattheweather.global.exception.ApplicationException;
import com.weather.the.what.whattheweather.global.exception.ApplicationExceptionStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ErrorResponse(
        String statusCode,
        String message,
        String method,
        String path,
        String timestamp
) {

    private static final String TIMESTAMP = "yyyyMMddHHmmss";

    public ErrorResponse(
            String statusCode,
            String message,
            String method,
            String path,
            String timestamp
    ) {
        this.statusCode = statusCode;
        this.message = message;
        this.method = method;
        this.path = path;
        this.timestamp = StringUtils.hasText(timestamp) ?
                timestamp : LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP));
    }

    public static ErrorResponse create(HttpServletRequest request) {
        return new ErrorResponse(
                ApplicationExceptionStatus.INTERNAL_SERVER_ERROR.getValue(),
                ApplicationExceptionStatus.INTERNAL_SERVER_ERROR.getMessage(),
                request.getMethod(),
                request.getRequestURI(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP))
        );
    }

    public static ErrorResponse from(
            HttpServletRequest request,
            ApplicationException exception
    ) {
        return new ErrorResponse(
                exception.getValue(),
                exception.getMessage(),
                request.getMethod(),
                request.getRequestURI(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP))
        );
    }
}
