package xyz.chengzi.ooad.repository;

import javax.annotation.Nonnull;

public interface Specification<T> {
    /**
     * Check if the given object is satisfied by this specification.
     *
     * @param t the object.
     * @return whether it is satisfied by this specification.
     */
    boolean isSatisfiedBy(@Nonnull T t);
}
