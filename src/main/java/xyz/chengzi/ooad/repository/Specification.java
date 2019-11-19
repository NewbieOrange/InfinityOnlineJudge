package xyz.chengzi.ooad.repository;

import javax.annotation.Nonnull;

public interface Specification<T> {
    /**
     * Return the maximum amount of results queried by this Specification.
     *
     * @return the max amount of results.
     */
    default int getMaxResults() {
        return -1;
    }
}
