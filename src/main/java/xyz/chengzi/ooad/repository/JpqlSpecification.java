package xyz.chengzi.ooad.repository;

import javax.annotation.Nonnull;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Map;

public interface JpqlSpecification<T> extends Specification<T> {
    /**
     * Get the jpql query of this specification.
     *
     * @return the jpql query.
     */
    @Nonnull
    String toJpqlQuery();

    /**
     * Get the parameters of this specification.
     *
     * @return the parameters.
     */
    @Nonnull
    Map<String, Object> getJpqlParameters();

    /**
     * Add the parameters to the given query.
     *
     * @param query the query.
     * @return the given query, returned for convenience.
     */
    @Nonnull
    default Query prepare(@Nonnull Query query) {
        for (Map.Entry<String, Object> parameter : getJpqlParameters().entrySet()) {
            query.setParameter(parameter.getKey(), parameter.getValue());
        }
        if (getMaxResults() >= 0) {
            query.setMaxResults(getMaxResults());
        }
        return query;
    }

    /**
     * Add the parameters to the given query.
     *
     * @param typedQuery the query.
     * @return the given query, returned for convenience.
     */
    @Nonnull
    default TypedQuery<T> prepare(@Nonnull TypedQuery<T> typedQuery) {
        return (TypedQuery<T>) prepare((Query) typedQuery);
    }
}
