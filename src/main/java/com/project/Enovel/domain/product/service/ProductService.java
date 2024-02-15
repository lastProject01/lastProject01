package com.project.Enovel.domain.product.service;

import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Product modifyProduct(Product product,
                              String productName,
                              String category,
                              int price,
                              String productImg,
                              String introduceImg) {

        Product modifyProduct = product.toBuilder()
                .productName(productName)
                .category(category)
                .price(price)
                .productImg(productImg)
                .introduceImg(introduceImg)
                .build();

        this.productRepository.save(modifyProduct);

        return modifyProduct;

    }
}
