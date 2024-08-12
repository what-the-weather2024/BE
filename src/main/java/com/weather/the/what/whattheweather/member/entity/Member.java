package com.weather.the.what.whattheweather.member.entity;

import com.weather.the.what.whattheweather.global.entity.BaseEntity;
import com.weather.the.what.whattheweather.member.type.SocialType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET deleted=true WHERE member_id=?")
@SQLRestriction(value = "deleted=false")
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 20, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "member_name", length = 20, nullable = false)
    private String name;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;
}
