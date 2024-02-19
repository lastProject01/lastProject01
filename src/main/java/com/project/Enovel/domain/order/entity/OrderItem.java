package com.project.Enovel.domain.order.entity;

import com.project.Enovel.global.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderItem extends BaseEntity {
    @ManyToOne
    private Orders order;
}
