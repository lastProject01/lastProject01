package com.project.Enovel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    void testFindById() {
        // Given
        Long productId = 122L;
        Optional<Product> optionalProduct = this.productRepository.findById(productId);

        // Then
        assertEquals(true, optionalProduct.isPresent());
        optionalProduct.ifPresent(product -> assertEquals("샘플 상품 3", product.getProductName()));
    }

    @Test
    @Transactional
    void testFindBySubjectLike() {
        // Given
        String subject = "사과";
        List<Product> productList = this.productRepository.findBySubjectLike(subject);

        // Then
        assertEquals(1, productList.size());
        Product product = productList.get(0);
        assertEquals("사과", product.getProductName());
    }
}
