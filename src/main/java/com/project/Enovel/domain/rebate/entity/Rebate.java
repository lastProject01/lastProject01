package com.project.Enovel.domain.rebate.entity;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.global.base.BaseEntity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@ToString
@AllArgsConstructor
public class Rebate extends BaseEntity {
    private int totalPrice;

    @OneToOne
    private Member member;
}
