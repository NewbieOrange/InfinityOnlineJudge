package xyz.chengzi.ooad.repository;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;

public interface Groups<T> {
    List<Expression<?>> toGroups(Root<T> poll);

    Groups<T> and(Groups<T> another);

    Class<T> getType();
}
