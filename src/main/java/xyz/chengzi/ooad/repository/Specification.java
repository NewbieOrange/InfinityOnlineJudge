package xyz.chengzi.ooad.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Specification<T> {
    boolean isSatisfiedBy(T t);

    Predicate toPredicate(Root<T> root, CriteriaBuilder criteriaBuilder);

    Specification<T> and(Specification<T> another);

    Specification<T> or(Specification<T> another);

    Class<T> getType();
}
