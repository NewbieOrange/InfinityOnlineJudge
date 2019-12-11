package xyz.chengzi.ooad.repository;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiSpecification<T> implements JpqlSpecification<T> {
    protected List<Specification<T>> specifications = new ArrayList<>();
    protected String appendString;

    public MultiSpecification(@Nonnull String appendString, @Nonnull Specification<T>... specifications) {
        this.appendString = appendString;
        for (Specification<T> specification : specifications) {
            add(specification);
        }
    }

    @Nonnull
    public MultiSpecification<T> add(@Nonnull Specification<T> specification) {
        specifications.add(specification);
        return this;
    }

    @Nonnull
    @Override
    public String toJpqlQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        for (Specification<T> specification : specifications) {
            queryBuilder.append(appendString).append(((JpqlSpecification<T>) specification).toJpqlQuery());
        }
        return queryBuilder.substring(appendString.length());
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
