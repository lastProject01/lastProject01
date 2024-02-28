package com.project.Enovel.domain.product.service;

import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getList() {
        return this.productRepository.findAll();
    }

    public Product getProduct(Long id) {
        Optional<Product> product = this.productRepository.findById(id);
        return product.get();
    }

    //상품 등록
    public Product createProduct(String productName,
                                 int price,
                                 String productImg,
                                 String content) {

        //상품 생성 코드
        Product product = Product.builder()
                .productName(productName)
                .price(price)
                .productImg(productImg)
                .content(content)
                .createDate(LocalDateTime.now())
                .build();

        //생성된 상품 저장
        this.productRepository.save(product);

        return product;
    }

    //상품 수정
    public Product modifyProduct(Product product,
                                 String productName,
                                 int price,
                                 String productImg,
                                 String content) {

        //상품 수정 코드
        Product modifyProduct = product.toBuilder()
                .productName(productName)
                .price(price)
                .productImg(productImg)
                .content(content)
                .modifyDate(LocalDateTime.now())
                .build();

        //수정된 상품 저장
        this.productRepository.save(modifyProduct);

        return product;
    }

    //상품 삭제 (soft delete)
    public Product deleteProduct(Product product) {

        //삭제 시간 추가 코드
        Product deleteProduct = product.toBuilder()
                .deleteDate(LocalDateTime.now())
                .build();

        this.productRepository.save(deleteProduct);

        return product;
    }

    public Product addProduct(Product product) {

        Product addProduct = product.toBuilder()
                .deleteDate(null)
                .build();

        this.productRepository.save(addProduct);

        return product;
    }
}
