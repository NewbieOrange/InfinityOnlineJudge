package xyz.chengzi.ooad.repository;

import javax.annotation.Nonnull;
import java.util.*;

public class OrSpecification<T> implements JpqlSpecification<T> {
    private List<Specification<T>> specifications = new ArrayList<>();

    public OrSpecification(@Nonnull Specification<T>... specifications) {
        for (Specification<T> specification : specifications) {
            add(specification);
        }
    }

    @Nonnull
    public OrSpecification<T> add(@Nonnull Specification<T> specification) {
        specifications.add(specification);
        return this;
    }

    @Nonnull
    @Override
    public String toJpqlQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        for (Specification<T> specification : specifications) {
            queryBuilder.append(" OR ").append(((JpqlSpecification<T>) specification).toJpqlQuery());
        }
        return queryBuilder.substring(" OR ".length());
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

    @Override
    public int getMaxResults() {
        return specifications.stream().max(Comparator.comparingInt(Specification::getMaxResults))
                .map(Specification::getMaxResults).orElse(Integer.MAX_VALUE);
    }
}
