package xyz.chengzi.ooad.repository.submission;

import xyz.chengzi.ooad.entity.Submission;
import xyz.chengzi.ooad.repository.AbstractOrders;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public class SubmissionByTimeMemoryOrder extends AbstractOrders<Submission> {
    @Override
    public Order[] toOrders(Root<Submission> poll, CriteriaBuilder criteriaBuilder) {
        return new Order[]{criteriaBuilder.asc(poll.get("timeUsage")), criteriaBuilder.asc(poll.get("memoryUsage"))};
    }
}
