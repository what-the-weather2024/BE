package com.weather.the.what.whattheweather.global.exception;

import com.weather.the.what.whattheweather.global.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<ErrorResponse> handleApplicationException(
            HttpServletRequest request,
            ApplicationException exception
    ) {
        log.info("{}: {}", exception.getClass().getSimpleName(), exception.getMessage(), exception);
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorResponse.of(request, exception));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    protected ResponseEntity<ErrorResponse> handleException(
            HttpServletRequest request,
            HandlerMethodValidationException exception
    ) {
        log.info("{}: {}", exception.getClass().getSimpleName(), exception.getMessage(), exception);
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.of(request, exception));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(
            HttpServletRequest request,
            Exception exception
    ) {
        log.info("{}: {}", exception.getClass().getSimpleName(), exception.getMessage(), exception);
        return ResponseEntity
                .internalServerError()
                .body(ErrorResponse.create(request));
    }
}
