package com.weather.the.what.whattheweather.auth.oauth2;

import java.util.Map;

public class GoogleOAuth2UserAttributes extends OAuth2Attributes {

    public GoogleOAuth2UserAttributes(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return super.attributes;
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getProfileImage() {
        return (String) attributes.get("picture");
    }
}
