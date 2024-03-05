package com.project.Enovel.domain.favorite.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavorite is a Querydsl query type for Favorite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavorite extends EntityPathBase<Favorite> {

    private static final long serialVersionUID = 1337536682L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavorite favorite = new QFavorite("favorite");

    public final com.project.Enovel.global.base.QBaseEntity _super = new com.project.Enovel.global.base.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final com.project.Enovel.domain.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final com.project.Enovel.domain.product.entity.QProduct product;

    public QFavorite(String variable) {
        this(Favorite.class, forVariable(variable), INITS);
    }

    public QFavorite(Path<? extends Favorite> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavorite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavorite(PathMetadata metadata, PathInits inits) {
        this(Favorite.class, metadata, inits);
    }

    public QFavorite(Class<? extends Favorite> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.project.Enovel.domain.member.entity.QMember(forProperty("member")) : null;
        this.product = inits.isInitialized("product") ? new com.project.Enovel.domain.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

