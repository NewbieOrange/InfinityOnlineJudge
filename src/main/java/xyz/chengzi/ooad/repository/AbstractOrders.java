package xyz.chengzi.ooad.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractOrders<T> implements Orders<T> {
    public abstract Order[] toOrders(Root<T> poll, CriteriaBuilder criteriaBuilder);

    @Override
    public Orders<T> and(Orders<T> another) {
        return new AndOrders<>(this, another);
    }

    @Override
    public Class<T> getType() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }
}
