package com.project.Enovel.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1318808440L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final com.project.Enovel.global.base.QBaseEntity _super = new com.project.Enovel.global.base.QBaseEntity(this);

    public final com.project.Enovel.domain.member.entity.QMember author;

    public final NumberPath<Double> avgStarScore = createNumber("avgStarScore", Double.class);

    public final com.project.Enovel.domain.cart.entity.QCart cart;

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final DateTimePath<java.time.LocalDateTime> deleteDate = createDateTime("deleteDate", java.time.LocalDateTime.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath introduceImg = createString("introduceImg");

    public final StringPath introduceImgName = createString("introduceImgName");

    public final StringPath introduceImgPath = createString("introduceImgPath");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath productImg = createString("productImg");

    public final StringPath productImgName = createString("productImgName");

    public final StringPath productImgPath = createString("productImgPath");

    public final StringPath productName = createString("productName");

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.project.Enovel.domain.member.entity.QMember(forProperty("author")) : null;
        this.cart = inits.isInitialized("cart") ? new com.project.Enovel.domain.cart.entity.QCart(forProperty("cart"), inits.get("cart")) : null;
    }

}

