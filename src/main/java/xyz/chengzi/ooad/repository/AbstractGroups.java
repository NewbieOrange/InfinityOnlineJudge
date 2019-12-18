package xyz.chengzi.ooad.repository;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractGroups<T> implements Groups<T> {
    @Override
    public abstract List<Expression<?>> toGroups(Root<T> poll);

    @Override
    public Groups<T> and(Groups<T> another) {
        return new AndGroups<>(this, another);
    }

    @Override
    public Class<T> getType() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }
}
