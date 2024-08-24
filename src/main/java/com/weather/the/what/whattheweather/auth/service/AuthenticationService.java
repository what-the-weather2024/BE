package com.weather.the.what.whattheweather.auth.service;

import com.weather.the.what.whattheweather.auth.Principal;
import com.weather.the.what.whattheweather.auth.dto.AuthResponse;
import com.weather.the.what.whattheweather.auth.dto.TokenResponse;
import com.weather.the.what.whattheweather.auth.oauth2.OAuth2UserInfo;
import com.weather.the.what.whattheweather.auth.oauth2.type.SocialType;
import com.weather.the.what.whattheweather.auth.repository.AuthenticationRepository;
import com.weather.the.what.whattheweather.auth.token.Token;
import com.weather.the.what.whattheweather.auth.token.TokenManager;
import com.weather.the.what.whattheweather.auth.token.TokenPayload;
import com.weather.the.what.whattheweather.member.entity.Member;
import com.weather.the.what.whattheweather.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService extends DefaultOAuth2UserService {

    private final MemberService memberService;
    private final TokenManager tokenManager;
    private final AuthenticationRepository authenticationRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        SocialType socialType = SocialType.find(userRequest.getClientRegistration().getRegistrationId());
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(
                socialType,
                getUserNameAttributeName(userRequest),
                socialType.toOAuth2Attributes(oAuth2User.getAttributes()));
        Member member = memberService.getMemberFromOAuth2USerInfo(oAuth2UserInfo);
        return Principal.of(member);
    }

    public TokenPayload generateToken(Long memberId) {
        String accessToken = tokenManager.generateToken(Token.ACCESS_TOKEN, memberId);
        String refreshToken = tokenManager.generateToken(Token.REFRESH_TOKEN, memberId);
        authenticationRepository.saveToken(memberId, refreshToken, Token.REFRESH_TOKEN.getExpirationTime());
        return new TokenPayload(
                TokenResponse.of(accessToken, tokenManager.getExpiredTime(accessToken)),
                TokenResponse.of(refreshToken, tokenManager.getExpiredTime(refreshToken))
        );
    }

    public AuthResponse regenerateToken(String refreshToken) {
        final Long memberId = tokenManager.getMemberId(refreshToken);
        authenticationRepository.validateToken(memberId, refreshToken);
        final TokenPayload tokenPayload = generateToken(memberId);
        authenticationRepository.saveToken(memberId, tokenPayload.refreshToken().token(), Token.REFRESH_TOKEN.getExpirationTime());
        return AuthResponse.from(tokenPayload);
    }

    public Long getMemberId(String token) {
        return tokenManager.getMemberId(token);
    }

    private String getUserNameAttributeName(OAuth2UserRequest userRequest) {
        return userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
    }
}
