package com.project.Enovel.domain.product.service;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.member.service.MemberService;
import com.project.Enovel.domain.product.entity.Product;
import com.project.Enovel.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.util.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberService memberService;

    @Value("${custom.originPath}")
    private String originPath;

    public List<Product> getList() {
        return this.productRepository.findAll();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getList(String category) {
        if (StringUtils.isEmpty(category)) {
            return getAllProducts();
        } else {
            return getCategoryProducts(category);
        }
    }


    private List<Product> getCategoryProducts(String category) {
        List<Product> products = new ArrayList<>();

        switch (category) {
            case "NOVEL":
                products.addAll(getNovelProducts());
                break;
            case "POEM":
                products.addAll(getPoemProducts());
                break;
            case "ESSAY":
                products.addAll(getEssayProducts());
                break;
            case "MANAGEMENT":
                products.addAll(getManagementProducts());
                break;
            case "ECONOMY":
                products.addAll(getEconomyProducts());
                break;
            case "SOCIAL":
                products.addAll(getSocialProducts());
                break;
            default:
                products.addAll(getAllProducts());
                break;
        }

        return products;
    }

    public Product getProduct(Long id) {
        Optional<Product> product = this.productRepository.findById(id);
        return product.get();
    }

    //상품 등록
    public Product createProduct(String productName,
                                 int price,
                                 String productImg,
                                 String content,
                                 MultipartFile file,
                                 String category,
                                 Member author) throws IOException {

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/upload";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();
        String filePath = originPath + fileName;

        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);


        //상품 생성 코드
        Product product = Product.builder()
                .productName(productName)
                .price(price)
                .content(content)
                .productImgPath(filePath)
                .productImgName(fileName)
                .category(category)
                .author(author)
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
                                 String category,
                                 String content) {

        //상품 수정 코드
        Product modifyProduct = product.toBuilder()
                .productName(productName)
                .price(price)
                .content(content)
                .category(category)
                .modifyDate(LocalDateTime.now())
                .build();

        //수정된 상품 저장
        this.productRepository.save(modifyProduct);

        return product;
    }

    //상품 삭제 (soft delete)
    public Product deleteProduct(Product product, Member member, Principal principal) {
        // 현재 사용자 정보 가져오기
        Member currentUser = this.memberService.getMember(principal.getName());

        // 상품 작성자 정보 가져오기
        Member productOwner = product.getAuthor();

        // 현재 사용자와 상품 작성자가 동일한 경우에만 삭제 처리
        if (member.isCheckedAdmin() || member.isCheckedSeller() && currentUser.equals(productOwner)) {
            // 삭제 시간 추가 코드
            Product deleteProduct = product.toBuilder()
                    .deleteDate(LocalDateTime.now())
                    .build();

            this.productRepository.save(deleteProduct);

            return deleteProduct;
        } else {
            // 권한이 없는 경우 익셉션 발생
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "상품을 수정할 권한이 없습니다.");
        }
    }

    public Product addProduct(Product product, Member member, Principal principal) {
        // 현재 사용자 정보 가져오기
        Member currentUser = this.memberService.getMember(principal.getName());

        // 상품 작성자 정보 가져오기
        Member productOwner = product.getAuthor();

        // 현재 사용자와 상품 작성자가 동일한 경우에만 삭제 처리
        if (member.isCheckedAdmin() || member.isCheckedSeller() && currentUser.equals(productOwner)) {
            // 삭제 시간 추가 코드
            Product addProduct = product.toBuilder()
                    .deleteDate(null)
                    .build();

            this.productRepository.save(addProduct);

            return null;
        } else {
            // 권한이 없는 경우 익셉션 발생
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "상품을 수정할 권한이 없습니다.");
        }
    }


    public List<Product> getNovelProducts() {
        return this.productRepository.findByNovel();
    }

    public List<Product> getPoemProducts() {
        return this.productRepository.findByPoem();
    }

    public List<Product> getEssayProducts() {
        return this.productRepository.findByEssay();
    }
    public List<Product> getManagementProducts() {
        return this.productRepository.findByManagement();
    }
    public List<Product> getEconomyProducts() {
        return this.productRepository.findByEconomy();
    }
    public List<Product> getSocialProducts() {
        return this.productRepository.findBySocial();
    }
}
