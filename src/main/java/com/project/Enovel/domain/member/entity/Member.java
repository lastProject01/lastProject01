package com.project.Enovel.domain.member.entity;

import com.project.Enovel.domain.cart.entity.Cart;
import com.project.Enovel.global.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {
    @Column(unique = true)
    private String username;

    @Setter
    private String password;

    @Column(unique = true)
    private String nickname;

    @Email
    @Column(unique = true)
    private String email;

    private String mailKey;

    private String address;

    private String phone;

    @Setter
    private boolean checkedSeller;

    @Setter
    private boolean checkedAdmin;

    @Setter
    private boolean checkedDeleted;

    @Setter
    private boolean checkedKakaoMember;

//    @OneToOne
//    private Cart cart;
}
