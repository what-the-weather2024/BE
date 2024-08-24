package com.weather.the.what.whattheweather.auth.controller;

import com.weather.the.what.whattheweather.auth.dto.AuthResponse;
import com.weather.the.what.whattheweather.auth.service.AuthenticationService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @RequestHeader @NotEmpty String refreshToken
    ) {
        return ResponseEntity
                .ok()
                .body(authenticationService.regenerateToken(refreshToken));
    }
}
