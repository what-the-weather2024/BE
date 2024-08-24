package com.weather.the.what.whattheweather.auth.oauth2;

import com.weather.the.what.whattheweather.auth.oauth2.type.SocialType;
import com.weather.the.what.whattheweather.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2UserInfo {

    private final SocialType socialType;
    private final String nameAttributeKey;
    private final OAuth2Attributes oauth2Attributes;

    public static OAuth2UserInfo of(SocialType socialType, String nameAttributeKey, OAuth2Attributes oAuth2Attributes) {
        return new OAuth2UserInfo(socialType, nameAttributeKey, oAuth2Attributes);
    }

    public Member toMember() {
        return Member.ofSocial(
                oauth2Attributes.getEmail(),
                oauth2Attributes.getName(),
                oauth2Attributes.getProfileImage(),
                socialType,
                nameAttributeKey);
    }

    public String getSocialId() {
        return nameAttributeKey;
    }

    public Map<String, Object> getAttributes() {
        return oauth2Attributes.getAttributes();
    }
}
