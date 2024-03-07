package com.project.Enovel.domain.product.repository;

import com.project.Enovel.domain.favorite.entity.Favorite;
import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    @Query(
//            "select distinct p " +
//                    "from Product p " +
//                    "where " +
//                    " p.name like %:kw% " +
//                    " and p.category = 'NOVEL'"
//    )
//    Page<Product> findAllNovelByKeyword(@Param("kw") String kw, Pageable pageable);
//
    @Query("select distinct p from Product p where p.category = 'NOVEL'")
    List<Product> findByNovel();

//    @Query(
//            "select distinct p " +
//                    "from Product p " +
//                    "where " +
//                    " p.name like %:kw% " +
//                    " and p.category = 'POEM'"
//    )
//    Page<Product> findAllPoemByKeyword(@Param("kw") String kw, Pageable pageable);

    @Query("select distinct p from Product p where p.category = 'POEM'")
    List<Product> findByPoem();

//    @Query(
//            "select distinct p " +
//                    "from Product p " +
//                    "where " +
//                    " p.name like %:kw% " +
//                    " and p.category = 'ESSAY'"
//    )
//    Page<Product> findAllEssayByKeyword(@Param("kw") String kw, Pageable pageable);

    @Query("select distinct p from Product p where p.category = 'ESSAY'")
    List<Product> findByEssay();

//    @Query(
//            "select distinct p " +
//                    "from Product p " +
//                    "where " +
//                    " p.name like %:kw% " +
//                    " and p.category = 'MANAGEMENT'"
//    )
//    Page<Product> findAllManagementByKeyword(@Param("kw") String kw, Pageable pageable);

    @Query("select distinct p from Product p where p.category = 'MANAGEMENT'")
    List<Product> findByManagement();


//    @Query(
//            "select distinct p " +
//                    "from Product p " +
//                    "where " +
//                    " p.name like %:kw% " +
//                    " and p.category = 'SOCIAL'"
//    )
//    Page<Product> findAllSocialByKeyword(@Param("kw") String kw, Pageable pageable);

    @Query("select distinct p from Product p where p.category = 'SOCIAL'")
    List<Product> findBySocial();


    List<Product> findByAuthor(Member author);

}
