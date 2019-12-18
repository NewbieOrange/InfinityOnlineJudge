package xyz.chengzi.ooad.repository.submission;

import xyz.chengzi.ooad.entity.Problem;
import xyz.chengzi.ooad.entity.Submission;
import xyz.chengzi.ooad.repository.AbstractSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SubmissionFromProblemSpecification extends AbstractSpecification<Submission> {
    private Problem problem;

    public SubmissionFromProblemSpecification(Problem problem) {
        this.problem = problem;
    }

    @Override
    public boolean isSatisfiedBy(Submission submission) {
        return submission.getProblem().equals(problem);
    }

    @Override
    public Predicate toPredicate(Root<Submission> poll, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(poll.get("problem"), problem);
    }
}
