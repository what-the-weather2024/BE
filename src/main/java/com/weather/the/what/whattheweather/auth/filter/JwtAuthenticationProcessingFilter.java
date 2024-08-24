package com.weather.the.what.whattheweather.auth.filter;

import com.weather.the.what.whattheweather.auth.Principal;
import com.weather.the.what.whattheweather.auth.service.AuthenticationService;
import com.weather.the.what.whattheweather.auth.token.Token;
import com.weather.the.what.whattheweather.global.exception.ApplicationException;
import com.weather.the.what.whattheweather.global.exception.ApplicationExceptionStatus;
import com.weather.the.what.whattheweather.member.entity.Member;
import com.weather.the.what.whattheweather.member.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private static final RequestMatcher AUTH_REFRESH_REQUEST = new AntPathRequestMatcher("/auth/refresh");

    private static final String PREFIX = "Bearer ";
    private static final String REMOVE = "";

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final AuthenticationService authenticationService;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (AUTH_REFRESH_REQUEST.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        Optional.ofNullable(request.getHeader(Token.ACCESS_TOKEN.getHeaderName()))
                .map(this::removePrefix)
                .ifPresent(accessToken -> {
                    final Member member = memberService.getMemberById(authenticationService.getMemberId(accessToken));
                    saveAuthentication(new Principal(member));
                });
        filterChain.doFilter(request, response);
    }

    private String removePrefix(String token) {
        validateTokenType(token);
        return token.replace(PREFIX, REMOVE);
    }

    private void validateTokenType(String token) {
        if (!token.startsWith(PREFIX)) {
            throw new ApplicationException(ApplicationExceptionStatus.UNSUPPORTED_TOKEN_TYPE);
        }
    }

    private void saveAuthentication(Principal principal) {
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(
                principal,
                null,
                authoritiesMapper.mapAuthorities(principal.getAuthorities()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
