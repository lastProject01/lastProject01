package com.project.Enovel.domain.order.repository;

import com.project.Enovel.domain.member.entity.Member;
import com.project.Enovel.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Order> search(Member buyer, Boolean payStatus, Boolean cancelStatus, Boolean refundStatus, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> orderRoot = cq.from(Order.class);
        List<Predicate> predicates = new ArrayList<>();

        // 조건에 따른 Predicate 생성
        if (buyer != null) {
            predicates.add(cb.equal(orderRoot.get("buyer"), buyer));
        }
        if (payStatus != null) {
            predicates.add(payStatus ? cb.isNotNull(orderRoot.get("payDate")) : cb.isNull(orderRoot.get("payDate")));
        }
        if (cancelStatus != null) {
            predicates.add(cancelStatus ? cb.isNotNull(orderRoot.get("cancelDate")) : cb.isNull(orderRoot.get("cancelDate")));
        }
        if (refundStatus != null) {
            predicates.add(refundStatus ? cb.isNotNull(orderRoot.get("refundDate")) : cb.isNull(orderRoot.get("refundDate")));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        TypedQuery<Order> query = entityManager.createQuery(cq);
        int totalRows = query.getResultList().size(); // 전체 결과 수

        // 페이지네이션 적용
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Order> resultList = query.getResultList();

        return new PageImpl<>(resultList, pageable, totalRows);
    }
}
