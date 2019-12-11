package xyz.chengzi.ooad.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;

abstract public class AbstractSpecification<T> implements Specification<T> {
    @Override
    public abstract boolean isSatisfiedBy(T t);

    @Override
    public abstract Predicate toPredicate(Root<T> poll, CriteriaBuilder criteriaBuilder);

    @Override
    public Specification<T> and(Specification<T> another) {
        return new AndSpecification<>(this, another);
    }

    @Override
    public Specification<T> or(Specification<T> another) {
        return new OrSpecification<>(this, another);
    }

    @Override
    public Class<T> getType() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }
}
