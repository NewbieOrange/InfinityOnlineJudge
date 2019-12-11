package xyz.chengzi.ooad.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AndSpecification<T> extends AbstractSpecification<T> {
    private Specification<T> first, second;

    public AndSpecification(Specification<T> first, Specification<T> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean isSatisfiedBy(T t) {
        return first.isSatisfiedBy(t) && second.isSatisfiedBy(t);
    }

    @Override
    public Predicate toPredicate(Root<T> poll, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(first.toPredicate(poll, criteriaBuilder), second.toPredicate(poll, criteriaBuilder));
    }
}
