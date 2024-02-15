package com.project.Enovel.domain.product.service;

import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    //상품 등록
    public Product createProduct(String productName,
                              String category,
                              int price,
                              String productImg,
                              String introduceImg) {

        Product product = Product.builder()
                .productName(productName)
                .category(category)
                .price(price)
                .productImg(productImg)
                .introduceImg(introduceImg)
                .build();

        this.productRepository.save(product);

        return product;
    }
}
