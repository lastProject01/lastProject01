package com.project.Enovel.domain.member.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberCreateForm {
    @NotEmpty(message = "ID를 입력해 주십시오.")
    @Size(min = 5, max = 15, message = "사용자ID는 5자 이상, 15자 이하로 입력해주세요.")
    private String username;

    @NotEmpty(message = "비밀번호를 입력하여 주십시오.")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수입니다.")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
    private String password2;

    @NotEmpty(message = "닉네임을 입력해 주십시오.")
    private String nickname;

    @NotEmpty(message = "이메일을 입력해 주십시오.")
    private String email;

    @NotEmpty(message = "이메일 인증코드를 입력해 주십시오.")
    private String mailKey;

    @NotEmpty(message = "주소를 입력해 주십시오.")
    private String address;

    @NotEmpty(message = "전화번호를 입력해 주십시오.")
    @Size(min = 11, max = 11)
    private String phone;

}
