package com.weather.the.what.whattheweather.auth.token;

public interface TokenGenerator {

    String generateToken(Token token, Long memberId);
}
