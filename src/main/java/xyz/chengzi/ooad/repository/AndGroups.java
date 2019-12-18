package xyz.chengzi.ooad.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;

public class AndGroups<T> extends AbstractGroups<T> {
    private Groups<T> first, second;

    public AndGroups(Groups<T> first, Groups<T> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public List<Expression<?>> toGroups(Root<T> poll) {
        List<Expression<?>> expressions = first.toGroups(poll);
        expressions.addAll(second.toGroups(poll));
        return expressions;
    }
}
