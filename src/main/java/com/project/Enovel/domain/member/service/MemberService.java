package com.project.Enovel.domain.member.service;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.repository.MemberRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member create(String username, String password, String nickname, String email,
                         String address, String phone, boolean checkedSeller, boolean checkedAdmin) {
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .email(email)
                .address(address)
                .phone(phone)
                .checkedSeller(checkedSeller)
                .checkedAdmin(checkedAdmin)
                .createDate(LocalDateTime.now())
                .build();
        this.memberRepository.save(member);
        return member;
    }

    @Transactional
    public void modify(Member member, String nickname, String email, String address, String phone) {
        this.memberRepository.findByUsername(member.getUsername())
                .ifPresent(updateUser -> {
                    Member modifyMember = Member.builder()
                            .id(updateUser.getId())
                            .username(member.getUsername())
                            .password(member.getPassword())
                            .nickname(nickname)
                            .email(email)
                            .address(address)
                            .phone(phone)
                            .createDate(member.getCreateDate())
                            .modifyDate(LocalDateTime.now())
                            .checkedSeller(member.isCheckedSeller())
                            .checkedAdmin(member.isCheckedAdmin())
                            .checkedDeleted(member.isCheckedDeleted())
                            .build();
                    this.memberRepository.save(modifyMember);
                });
    }

    @Transactional
    public void changePw(Member member, String password) {
        this.memberRepository.findByUsername(member.getUsername())
                .ifPresent(updateUser -> {
                    Member modifyMember = Member.builder()
                            .id(updateUser.getId())
                            .username(member.getUsername())
                            .password(passwordEncoder.encode(password))
                            .nickname(member.getNickname())
                            .email(member.getEmail())
                            .address(member.getAddress())
                            .phone(member.getPhone())
                            .createDate(member.getCreateDate())
                            .modifyDate(LocalDateTime.now())
                            .checkedSeller(member.isCheckedSeller())
                            .checkedAdmin(member.isCheckedAdmin())
                            .checkedDeleted(member.isCheckedDeleted())
                            .build();
                    this.memberRepository.save(modifyMember);
                });
    }

    public Page<Member> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 1000, Sort.by(sorts));
        Specification<Member> spec = search(kw);
        return this.memberRepository.findAll(spec, pageable);
    }

    private Specification<Member> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Member> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                return cb.or(
                        cb.like(q.get("nickname"), "%" + kw + "%"),
                        cb.like(q.get("username"), "%" + kw + "%")
                );
            };
        };
    }

    public void deleteMember(String username) {
        Optional<Member> optionalUser = memberRepository.findByUsername(username);
        Member deleteuser = optionalUser.get();
        deleteuser.setCheckedDeleted(true);
        this.memberRepository.save(deleteuser);
    }

    public void sellerMember(String username) {
        Optional<Member> optionalUser = memberRepository.findByUsername(username);
        Member sellerMember = optionalUser.get();
        sellerMember.setCheckedSeller(true);
        this.memberRepository.save(sellerMember);
    }

    public void commonMember(String username) {
        Optional<Member> optionalUser = memberRepository.findByUsername(username);
        Member commonMember = optionalUser.get();
        commonMember.setCheckedSeller(false);
        this.memberRepository.save(commonMember);
    }

    public Member getMember(String username) {
        return this.memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    public Member getMemberFindById(Long id) {
        Optional<Member> member = this.memberRepository.findById(id);
        return member.get();
    }

    //getMember 부분 충돌이 발생할 가능성이 있음
}
