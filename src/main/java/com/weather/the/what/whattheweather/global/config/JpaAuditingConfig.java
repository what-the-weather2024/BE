package com.weather.the.what.whattheweather.global.config;

import com.weather.the.what.whattheweather.auth.Principal;
import com.weather.the.what.whattheweather.global.exception.ApplicationException;
import com.weather.the.what.whattheweather.global.exception.ApplicationExceptionStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(authentication -> {
                    validateAuthentication(authentication);
                    final Principal loginUser = (Principal) authentication.getPrincipal();
                    return loginUser.getMemberId();
                });
    }

    private void validateAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ApplicationException(ApplicationExceptionStatus.UNAUTHORIZED);
        }
    }
}
