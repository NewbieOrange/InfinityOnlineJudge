package xyz.chengzi.ooad.repository;

import javax.annotation.Nonnull;

public class OrSpecification<T> extends MultiSpecification<T> {
    public OrSpecification(@Nonnull Specification<T>... specifications) {
        super(" OR ", specifications);
    }
}
