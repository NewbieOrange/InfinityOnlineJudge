package xyz.chengzi.ooad.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public class EmptyOrder<T> extends AbstractOrders<T> {
    @Override
    public Order[] toOrders(Root<T> poll, CriteriaBuilder criteriaBuilder) {
        return new Order[0];
    }
}
