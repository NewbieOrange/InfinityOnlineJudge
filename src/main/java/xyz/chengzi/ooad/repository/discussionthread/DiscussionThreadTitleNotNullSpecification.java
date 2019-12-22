package xyz.chengzi.ooad.repository.discussionthread;

import xyz.chengzi.ooad.entity.DiscussionThread;
import xyz.chengzi.ooad.repository.AbstractSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DiscussionThreadTitleNotNullSpecification extends AbstractSpecification<DiscussionThread> {
    @Override
    public boolean isSatisfiedBy(DiscussionThread discussionThread) {
        return discussionThread.getTitle() != null;
    }

    @Override
    public Predicate toPredicate(Root<DiscussionThread> poll, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isNotNull(poll.get("title"));
    }
}
