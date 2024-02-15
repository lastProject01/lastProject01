package com.project.Enovel.domain.member.entity;

import com.project.Enovel.global.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class Member extends BaseEntity {
    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String nickname;

    @Email
    @Column(unique = true)
    private String email;

    private String mailKey;

    private String address;

    @Column(unique = true)
    private String phone;

    private boolean checkedSeller;

    private boolean checkedAdmin;

    private boolean checkedDeleted;
}
