package xyz.chengzi.ooad.repository;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;

public class EmptySpecification<T> implements JpqlSpecification<T>
{
    @Override public boolean isSatisfiedBy(@Nonnull T t)
    {
        return true;
    }

    @Nonnull
    @Override public String toJpqlQuery()
    {
        return "TRUE = TRUE";
    }

    @Nonnull
    @Override public Map<String, Object> getJpqlParameters()
    {
        return Collections.emptyMap();
    }
}
