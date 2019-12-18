package xyz.chengzi.ooad.repository;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;

public class EmptyGroups<T> extends AbstractGroups<T> {
    @Override
    public List<Expression<?>> toGroups(Root<T> poll) {
        return List.of();
    }
}
