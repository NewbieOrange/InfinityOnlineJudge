package xyz.chengzi.ooad.repository.entity;

import com.google.common.collect.ImmutableMap;
import xyz.chengzi.ooad.repository.JpqlSpecification;

import javax.annotation.Nonnull;
import java.util.Map;

public class SinceIdSpecification<T> implements JpqlSpecification<T> {
    private int since;

    public SinceIdSpecification(int since) {
        this.since = since;
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
}
