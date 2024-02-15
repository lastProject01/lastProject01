package com.project.Enovel.domain.product.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateForm {

    private String productName;
    private String category;
    private int price;
    private String productImg;
    private String introduceImg;
    private boolean checkedProduct;
}
