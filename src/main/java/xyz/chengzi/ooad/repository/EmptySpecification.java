package xyz.chengzi.ooad.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EmptySpecification<T> extends AbstractSpecification<T> {
    @Override
    public boolean isSatisfiedBy(T t) {
        return true;
    }

    @Override
    public Predicate toPredicate(Root<T> poll, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and();
    }
}
