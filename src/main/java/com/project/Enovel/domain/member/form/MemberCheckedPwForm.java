package com.project.Enovel.domain.member.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCheckedPwForm {
    @NotEmpty(message = "비밀번호 재확인은 필수입니다.")
    private String checkedPassword;
}