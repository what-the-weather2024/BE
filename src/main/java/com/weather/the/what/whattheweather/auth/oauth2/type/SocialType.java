package com.weather.the.what.whattheweather.auth.oauth2.type;

import com.weather.the.what.whattheweather.auth.oauth2.GoogleOAuth2UserAttributes;
import com.weather.the.what.whattheweather.auth.oauth2.OAuth2Attributes;
import com.weather.the.what.whattheweather.global.exception.ApplicationException;
import com.weather.the.what.whattheweather.global.exception.ApplicationExceptionStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum SocialType {

    GOOGLE("google") {
        @Override
        public OAuth2Attributes toOAuth2Attributes(Map<String, Object> attributes) {
            return new GoogleOAuth2UserAttributes(attributes);
        }
    };

    private final String registrationId;

    public static SocialType find(String registrationId) {
        return Arrays.stream(values())
                .filter(value -> value.registrationId.equals(registrationId))
                .findAny()
                .orElseThrow(() -> new ApplicationException(ApplicationExceptionStatus.UNSUPPORTED_SOCIAL_TYPE));
    }

    public abstract OAuth2Attributes toOAuth2Attributes(Map<String, Object> attributes);
}
