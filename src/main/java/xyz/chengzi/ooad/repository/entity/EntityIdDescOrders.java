package xyz.chengzi.ooad.repository.entity;

import xyz.chengzi.ooad.repository.AbstractOrders;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public class EntityIdDescOrders<T> extends AbstractOrders<T> {
    @Override
    public Order[] toOrders(Root<T> poll, CriteriaBuilder criteriaBuilder) {
        return new Order[]{criteriaBuilder.desc(poll.get("id"))};
    }
}
