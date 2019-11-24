package xyz.chengzi.ooad.embeddable;

import xyz.chengzi.ooad.entity.Problem;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Embeddable
public class ContestProblem {
    private String shortName;
    @OneToOne
    private Problem problem;
    private Integer acceptedAmount;
    private Integer submissionAmount;
}
