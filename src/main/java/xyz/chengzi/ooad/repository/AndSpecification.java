package xyz.chengzi.ooad.repository;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AndSpecification<T> implements JpqlSpecification<T> {
    private List<Specification<T>> specifications = new ArrayList<>();

    public AndSpecification(@Nonnull Specification<T>... specifications) {
        for (Specification<T> specification : specifications) {
            add(specification);
        }
    }

    @Nonnull
    public AndSpecification<T> add(@Nonnull Specification<T> specification) {
        specifications.add(specification);
        return this;
    }

    @Override
    public boolean isSatisfiedBy(@Nonnull T t) {
        for (Specification<T> specification : specifications) {
            if (!specification.isSatisfiedBy(t)) {
                return false;
            }
        }
        return true;
    }

    @Nonnull
    @Override
    public String toJpqlQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        for (Specification<T> specification : specifications) {
            queryBuilder.append(" AND ").append(((JpqlSpecification<T>) specification).toJpqlQuery());
        }
        return queryBuilder.substring(" AND ".length());
    }

    @Nonnull
    @Override
    public Map<String, Object> getJpqlParameters() {
        Map<String, Object> parameters = new HashMap<>();
        for (Specification<T> specification : specifications) {
            parameters.putAll(((JpqlSpecification<T>) specification).getJpqlParameters());
        }
        return parameters;
    }
}