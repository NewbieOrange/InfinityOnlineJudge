package xyz.chengzi.ooad.repository.submission;

import xyz.chengzi.ooad.entity.Submission;
import xyz.chengzi.ooad.entity.User;
import xyz.chengzi.ooad.repository.AbstractSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SubmissionByUserSpecification extends AbstractSpecification<Submission> {
    private User user;

    public SubmissionByUserSpecification(User user) {
        this.user = user;
    }

    @Override
    public boolean isSatisfiedBy(Submission submission) {
        return submission.getUser().equals(user);
    }

    @Override
    public Predicate toPredicate(Root<Submission> poll, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(poll.get("user"), user);
    }
}
