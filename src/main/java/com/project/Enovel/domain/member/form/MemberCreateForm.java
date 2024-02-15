package com.project.Enovel.domain.member.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberCreateForm {
    @NotEmpty(message = "")
    @Size(min = 5, max = 15, message = "사용자ID는 5자 이상, 15자 이하로 입력해주세요.")
    private String usermane;
}
