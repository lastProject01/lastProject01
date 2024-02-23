package com.project.Enovel.domain.favorite.repository;

import com.project.Enovel.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Member, Long> {

}
