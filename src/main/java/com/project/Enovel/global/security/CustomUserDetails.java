package com.project.Enovel.global.security;

import com.project.Enovel.domain.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class CustomUserDetails implements UserDetails {
    private final Member user;

    public CustomUserDetails(Member user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 사용자의 역할에 따라 권한을 부여
        if (user.isCheckedAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER_ADMIN"));
        }

        if (user.isCheckedSeller()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER_SELLER"));
        }

        // 기본적인 USER 권한은 모든 사용자에게 부여
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return authorities;
    }
}
