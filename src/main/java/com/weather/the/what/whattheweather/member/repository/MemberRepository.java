package com.weather.the.what.whattheweather.member.repository;

import com.weather.the.what.whattheweather.auth.oauth2.type.SocialType;
import com.weather.the.what.whattheweather.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    Optional<Member> findByUsername(String username);
}
