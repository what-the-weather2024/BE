package com.weather.the.what.whattheweather.auth.oauth2;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public abstract class OAuth2Attributes {

    protected Map<String, Object> attributes;

    public abstract Map<String, Object> getAttributes();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getProfileImage();
}
