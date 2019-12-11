package xyz.chengzi.ooad.repository;

import com.google.common.collect.ObjectArrays;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public class AndOrders<T> extends AbstractOrders<T> {
    private Orders<T> first, second;

    public AndOrders(Orders<T> first, Orders<T> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public Order[] toOrders(Root<T> poll, CriteriaBuilder criteriaBuilder) {
        return ObjectArrays
                .concat(first.toOrders(poll, criteriaBuilder), second.toOrders(poll, criteriaBuilder), Order.class);
    }
}
