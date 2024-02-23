package com.project.Enovel.domain.product.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateForm {

    @NotEmpty(message = "상품명을 입력해 주세요")
    private String productName;

    @NotNull(message = "가격을 입력해 주세요")
    private int price;

    //@NotEmpty(message = "이미지를 넣어 주세요")
    private String productImgPath;

    private String productImgName;

    @NotEmpty(message = "내용을 넣어 주세요")
    private String content;

}
