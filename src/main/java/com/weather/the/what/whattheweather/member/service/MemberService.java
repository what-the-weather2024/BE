package com.weather.the.what.whattheweather.member.service;

import com.weather.the.what.whattheweather.auth.oauth2.OAuth2UserInfo;
import com.weather.the.what.whattheweather.global.exception.ApplicationException;
import com.weather.the.what.whattheweather.global.exception.ApplicationExceptionStatus;
import com.weather.the.what.whattheweather.member.entity.Member;
import com.weather.the.what.whattheweather.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member getMemberFromOAuth2USerInfo(OAuth2UserInfo oAuth2UserInfo) {
        return memberRepository.findBySocialTypeAndSocialId(oAuth2UserInfo.getSocialType(), oAuth2UserInfo.getSocialId())
                .orElseGet(() -> memberRepository.save(oAuth2UserInfo.toMember()));
    }

    @Transactional(readOnly = true)
    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new ApplicationException(ApplicationExceptionStatus.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ApplicationException(ApplicationExceptionStatus.MEMBER_NOT_FOUND));
    }
}
