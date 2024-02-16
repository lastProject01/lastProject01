package com.project.Enovel.global.security;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.repository.MemberRepository;
import com.project.Enovel.domain.member.role.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> _member = this.memberRepository.findByUsername(username);
        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        Member member = _member.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (member.isCheckedAdmin()) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else if (member.isCheckedSeller()) {
            authorities.add((new SimpleGrantedAuthority(UserRole.SELLER.getValue())));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(member.getUsername(), member.getPassword(), authorities);
    }

}
