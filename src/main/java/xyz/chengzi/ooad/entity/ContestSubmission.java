package xyz.chengzi.ooad.entity;

import javax.persistence.*;

@Entity
@Table(name = "contest_submissions")
public class ContestSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Contest contest;
    @OneToOne
    private Submission submission;
}
