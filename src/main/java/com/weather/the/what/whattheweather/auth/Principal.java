package com.weather.the.what.whattheweather.auth;

import com.weather.the.what.whattheweather.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class Principal implements OAuth2User {

    private final Member member;

    public static OAuth2User of(Member member) {
        return new Principal(member);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getName() {
        return member.getName();
    }

    public Long getMemberId() {
        return member.getId();
    }

    public String getUsername() {
        return member.getUsername();
    }
}
