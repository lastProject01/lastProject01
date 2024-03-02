package com.project.Enovel.domain.member.service;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.repository.MemberRepository;
import com.project.Enovel.domain.member.role.UserRole;
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
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final Set<String> registeredUsernames = new HashSet<>();

    public boolean isUsernameUnique(Long id) {
        // 실제로는 데이터베이스에서 해당 username이 존재하는지 확인합니다.
        Optional<Member> existingMember = memberRepository.findById(id);
        return existingMember.isEmpty();
    }

    public boolean isUsernameUnique(String username) {
        return !registeredUsernames.contains(username);
    }

    @Transactional
    public Member create(String username, String password, String nickname, String email,
                         String address, String phone, boolean checkedAdmin, boolean checkedSeller, boolean checkedKakaoMember) {
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .email(email)
                .address(address)
                .phone(phone)
                .createDate(LocalDateTime.now())
                .checkedSeller(false)
                .checkedAdmin(false)
                .checkedKakaoMember(false)
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
//  관리자 권한 시작
    @Transactional
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

    @Transactional
    public void deleteMember(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        Member deleteuser = member.get();
        deleteuser.setCheckedDeleted(true);
        this.memberRepository.save(deleteuser);
    }

    @Transactional
    public void sellerMember(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        Member sellerMember = member.get();
        sellerMember.setCheckedSeller(true);
        this.memberRepository.save(sellerMember);
    }

    @Transactional
    public void commonMember(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        Member commonMember = member.get();
        commonMember.setCheckedSeller(false);

        this.memberRepository.save(commonMember);
    }
//  관리자 권한 종료
    @Transactional
    public Member getMember(String username) {
        return this.memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    @Transactional
    public Member getMemberFindById(Long id) {
        Optional<Member> member = this.memberRepository.findById(id);
        return member.get();
    }

    @Transactional
    public Member whenSocialLogin(String providerTypeCode, String username, String nickname) {
        // 주어진 username으로 이미 가입된 회원이 있는지 확인
        Optional<Member> member = memberRepository.findByUsername(username);

        // 이미 가입된 회원이 있다면 해당 회원을 반환
        if (member.isPresent()) {
            return member.get();
        }

        // 가입된 회원이 없으면 새로운 회원을 생성하여 반환
        return create(username, "", nickname, "", "", "",false,false, true);
    }

}
