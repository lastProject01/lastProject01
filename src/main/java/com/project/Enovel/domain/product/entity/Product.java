package com.project.Enovel.domain.product.entity;

import com.project.Enovel.domain.cart.entity.Cart;
import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.global.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Entity
public class Product extends BaseEntity {

    private String productName;

    private String category;

    private int price;

    private String productImgPath;

    private String introduceImgPath;

    private String productImgName;

    private String introduceImgName;

    private String productImg;

    private String introduceImg;

    private double avgStarScore;

    private String content;

    private LocalDateTime deleteDate;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Member author;
}
