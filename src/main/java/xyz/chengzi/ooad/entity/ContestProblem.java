package xyz.chengzi.ooad.entity;

import javax.persistence.*;

@Entity
@Table(name = "contest_problems")
public class ContestProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Contest contest;
    private String shortName;
    @OneToOne
    private Problem problem;
    private Integer acceptedAmount;
    private Integer submissionAmount;
}
