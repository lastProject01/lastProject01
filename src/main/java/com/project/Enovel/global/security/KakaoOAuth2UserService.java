package com.project.Enovel.global.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KakaoOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // OAuth2UserService에서 사용자 정보를 가져옴
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 카카오 사용자 정보
        Map<String, Object> userAttributes = oAuth2User.getAttributes();

        // username 추출
        String username = (String) userAttributes.get("id");

        // username에 "KAKAO"가 포함되어 있는 경우 기본 권한만 부여
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (username.contains("KAKAO")) {
            // 특별한 권한을 부여하는 코드를 제거
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // DefaultOAuth2User 객체를 생성하여 반환
        return new DefaultOAuth2User(authorities, userAttributes, "id");
    }
}

