package com.weather.the.what.whattheweather.auth.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class JwtManager implements TokenManager {

    private static final String ID_CLAIM = "id";
    private static final String BEAR_TOKEN_PREFIX = "Bearer ";
    private static final String REMOVE = "";

    private final Key key;

    public JwtManager(@Value("${jwt.secretKey}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    @Override
    public String generateToken(Token token, Long memberId) {
        return Jwts.builder()
                .claims(toClaims(memberId))
                .expiration(token.getExpirationTimeDate())
                .signWith(key)
                .compact();
    }

    @Override
    public LocalDateTime getExpiredTime(String token) {
        return extract(token).expiredTime();
    }

    @Override
    public Long getMemberId(String token) {
        return extract(token).memberId();
    }

    private Claims toClaims(Long memberId) {
        return Jwts.claims()
                .add(ID_CLAIM, memberId)
                .build();
    }

    private JwtPayload extract(String token) {
        final Claims claims = parseClaims(token);
        final Long memberId = claims.get(ID_CLAIM, Long.class);
        final LocalDateTime expiredTime = LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
        return new JwtPayload(memberId, expiredTime);
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
