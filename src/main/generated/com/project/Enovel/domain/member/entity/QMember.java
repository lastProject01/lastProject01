package com.project.Enovel.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -171820314L;

    public static final QMember member = new QMember("member1");

    public final com.project.Enovel.global.base.QBaseEntity _super = new com.project.Enovel.global.base.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final BooleanPath checkedAdmin = createBoolean("checkedAdmin");

    public final BooleanPath checkedDeleted = createBoolean("checkedDeleted");

    public final BooleanPath checkedKakaoMember = createBoolean("checkedKakaoMember");

    public final BooleanPath checkedSeller = createBoolean("checkedSeller");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath email = createString("email");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath mailKey = createString("mailKey");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath username = createString("username");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

