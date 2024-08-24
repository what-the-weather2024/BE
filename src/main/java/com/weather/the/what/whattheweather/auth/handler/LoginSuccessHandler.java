package com.weather.the.what.whattheweather.auth.handler;

import com.weather.the.what.whattheweather.auth.Principal;
import com.weather.the.what.whattheweather.auth.dto.AuthResponse;
import com.weather.the.what.whattheweather.auth.service.AuthenticationService;
import com.weather.the.what.whattheweather.auth.token.TokenPayload;
import com.weather.the.what.whattheweather.global.util.JsonConverter;
import com.weather.the.what.whattheweather.member.entity.Member;
import com.weather.the.what.whattheweather.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthenticationService authenticationService;
    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Member member = memberService.getMemberByUsername(extractUsername(authentication));
        TokenPayload tokenPayload = authenticationService.generateToken(member.getId());
        setResponseBody(response, AuthResponse.from(tokenPayload));
    }

    private String extractUsername(Authentication authentication) {
        Principal principal = (Principal) authentication.getPrincipal();
        return principal.getUsername();
    }

    private void setResponseBody(HttpServletResponse response, AuthResponse authResponse) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(JsonConverter.getWriteValueAsString(authResponse));
    }
}
