package xyz.chengzi.ooad.repository;

import com.google.common.collect.ImmutableMap;

import javax.annotation.Nonnull;
import java.util.Map;

public class SinceIdSpecification<T> implements JpqlSpecification<T> {
    private int since, size;

    public SinceIdSpecification(int since, int size) {
        this.since = since;
        this.size = size;
    }

    @Nonnull
    @Override
    public String toJpqlQuery() {
        return "id >= :since" + hashCode();
    }

    @Nonnull
    @Override
    public Map<String, Object> getJpqlParameters() {
        return new ImmutableMap.Builder<String, Object>().put("since" + hashCode(), since).build();
    }

    @Override
    public int getMaxResults() {
        return size;
    }
}
