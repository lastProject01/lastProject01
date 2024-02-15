package com.project.Enovel.domain.product.entity;

import com.project.Enovel.global.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Entity
public class Product extends BaseEntity {
    String productName;
    String category;
    String subCategories;
    int price;
    String productImg;
    String introduceImg;
    double avgStarScore;

    @ManyToOne
    Member seller;

}
