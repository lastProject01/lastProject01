package com.project.Enovel.domain.member.repository;

import com.project.Enovel.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAjaxRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username);
}
