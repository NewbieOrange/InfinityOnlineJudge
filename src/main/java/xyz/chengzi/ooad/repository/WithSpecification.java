package xyz.chengzi.ooad.repository;

import javax.annotation.Nonnull;

public class WithSpecification<T> extends MultiSpecification<T> {
    public WithSpecification(@Nonnull Specification<T>... specifications) {
        super(" ", specifications);
    }
}
