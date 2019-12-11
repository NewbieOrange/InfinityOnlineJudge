package xyz.chengzi.ooad.repository.entity;

import com.google.common.collect.ImmutableMap;
import xyz.chengzi.ooad.repository.JpqlSpecification;

import javax.annotation.Nonnull;
import java.util.Map;

public class OrderByIdSpecification<T> implements JpqlSpecification<T> {
    private boolean asc;

    public OrderByIdSpecification(boolean desc) {
        this.asc = desc;
    }

    @Nonnull
    @Override
    public String toJpqlQuery() {
        return "ORDER BY id " + (asc ? "ASC" : "DESC");
    }

    @Nonnull
    @Override
    public Map<String, Object> getJpqlParameters() {
        return ImmutableMap.of();
    }
}
