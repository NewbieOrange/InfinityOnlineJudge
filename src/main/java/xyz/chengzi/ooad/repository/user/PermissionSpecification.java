package xyz.chengzi.ooad.repository.user;

import xyz.chengzi.ooad.entity.User;
import xyz.chengzi.ooad.repository.AbstractSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PermissionSpecification extends AbstractSpecification<User> {
    private String permission;

    public PermissionSpecification(String permission) {
        this.permission = permission;
    }

    @Override
    public boolean isSatisfiedBy(User user) {
        return user.hasPermission(permission);
    }

    @Override
    public Predicate toPredicate(Root<User> poll, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isMember(permission, poll.get("permissions"));
    }
}
