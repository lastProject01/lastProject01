package com.project.Enovel.domain.review.entity;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private Long orderItemId; // 나중에 @ManyToOne으로 변경 예정

    @Column(columnDefinition = "TEXT")
    private String content; // 댓글 내용

    // TODO: 이미지 처리 추가 예정
}
