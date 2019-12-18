package xyz.chengzi.ooad.repository.submission;

import xyz.chengzi.ooad.embeddable.SubmissionStatus;
import xyz.chengzi.ooad.entity.Submission;
import xyz.chengzi.ooad.repository.AbstractSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SubmissionStatusSpecification extends AbstractSpecification<Submission> {
    private SubmissionStatus status;

    public SubmissionStatusSpecification(SubmissionStatus status) {
        this.status = status;
    }

    @Override
    public boolean isSatisfiedBy(Submission submission) {
        return submission.getStatus() == status;
    }

    @Override
    public Predicate toPredicate(Root<Submission> poll, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(poll.get("status"), status);
    }
}
