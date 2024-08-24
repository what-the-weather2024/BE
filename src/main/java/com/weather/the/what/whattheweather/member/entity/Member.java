package com.weather.the.what.whattheweather.member.entity;

import com.weather.the.what.whattheweather.auth.oauth2.type.SocialType;
import com.weather.the.what.whattheweather.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET deleted=true WHERE member_id=?")
@SQLRestriction(value = "deleted=false")
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "member_name", length = 20, nullable = false)
    private String name;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;

    public Member(String username, String password, String name, String profileImageUrl, SocialType socialType, String socialId) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.socialType = socialType;
        this.socialId = socialId;
    }

    public static Member ofSocial(String username, String name, String profileImageUrl, SocialType socialType, String socialId) {
        return new Member(
                username,
                UUID.randomUUID().toString(),
                name,
                profileImageUrl,
                socialType,
                socialId
        );
    }
}
