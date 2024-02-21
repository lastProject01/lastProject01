package com.project.Enovel.domain.cart.entity;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Entity
public class Cart extends BaseEntity {

    @ManyToOne
   private Member member;

    @ManyToOne
    private Product product;
}
