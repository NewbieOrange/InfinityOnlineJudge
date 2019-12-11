package xyz.chengzi.ooad.repository.entity;

import xyz.chengzi.ooad.repository.AbstractSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SinceIdSpecification<T> extends AbstractSpecification<T> {
    private int since;

    public SinceIdSpecification(int since) {
        this.since = since;
    }

    @Override
    public boolean isSatisfiedBy(T t) {
        return false;
    }

    @Override
    public Predicate toPredicate(Root<T> poll, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.greaterThanOrEqualTo(poll.get("id"), since);
    }
}
