package com.project.Enovel.domain.member.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberMyPageForm {
    @NotEmpty(message = "닉네임을 입력해 주십시오.")
    private String nickname;

    @NotEmpty(message = "이메일을 입력해 주십시오.")
    private String email;

    @NotEmpty(message = "주소를 입력해 주십시오.")
    private String address;

    @NotEmpty(message = "전화번호를 입력해 주십시오.")
    @Size(min = 11, max = 11)
    private String phone;
}
