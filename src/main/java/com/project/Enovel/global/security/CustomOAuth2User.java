package com.project.Enovel.global.security;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.role.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class CustomOAuth2User extends User implements OAuth2User {
    public CustomOAuth2User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return getUsername();
    }

    // 사용자의 권한을 설정하는 부분
    public static CustomOAuth2User create(Member member) {
        List<GrantedAuthority> authorityList = new ArrayList<>();

        // 사용자가 관리자인 경우
        if (member.isCheckedAdmin()) {
            authorityList.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else if (member.isCheckedSeller()) {
            authorityList.add(new SimpleGrantedAuthority(UserRole.SELLER.getValue()));
        } else if (member.isCheckedKakaoMember()) {
            authorityList.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        } else {
            authorityList.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        return new CustomOAuth2User(member.getUsername(), member.getPassword(), authorityList);
    }
}
