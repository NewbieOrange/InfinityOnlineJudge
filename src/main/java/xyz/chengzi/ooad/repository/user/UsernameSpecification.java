package xyz.chengzi.ooad.repository.user;

import xyz.chengzi.ooad.entity.User;
import xyz.chengzi.ooad.repository.AbstractSpecification;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UsernameSpecification extends AbstractSpecification<User> {
    private String username;

    public UsernameSpecification(@Nonnull String username) {
        this.username = username;
    }

    @Override
    public boolean isSatisfiedBy(User user) {
        return user.getUsername().equals(username);
    }

    @Override
    public Predicate toPredicate(Root<User> poll, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(poll.get("username"), username);
    }
}
