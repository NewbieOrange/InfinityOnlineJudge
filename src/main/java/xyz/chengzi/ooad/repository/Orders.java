package xyz.chengzi.ooad.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public interface Orders<T> {
    Order[] toOrders(Root<T> poll, CriteriaBuilder criteriaBuilder);

    Orders<T> and(Orders<T> another);

    Class<T> getType();
}
