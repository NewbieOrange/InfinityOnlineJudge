package xyz.chengzi.ooad.repository;

import javax.annotation.Nonnull;

public class AndSpecification<T> extends MultiSpecification<T> {
    public AndSpecification(@Nonnull Specification<T>... specifications) {
        super(" AND ", specifications);
    }
}
