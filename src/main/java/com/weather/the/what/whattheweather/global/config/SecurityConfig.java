package com.weather.the.what.whattheweather.global.config;

import com.weather.the.what.whattheweather.auth.filter.JwtAuthenticationProcessingFilter;
import com.weather.the.what.whattheweather.auth.handler.LoginFailureHandler;
import com.weather.the.what.whattheweather.auth.handler.LoginSuccessHandler;
import com.weather.the.what.whattheweather.auth.service.AuthenticationService;
import com.weather.the.what.whattheweather.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/auth/refresh"
    };

    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final AuthenticationService authenticationService;
    private final MemberService memberService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler)
                        .userInfoEndpoint(userInfo -> userInfo.userService(authenticationService)))
                .addFilterAfter(jwtAuthenticationProcessingFilter(), LogoutFilter.class)
                .build();
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationProcessingFilter(authenticationService, memberService);
    }
}
