package xyz.chengzi.ooad.entity;

import xyz.chengzi.ooad.embeddable.SubmissionStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "submissions")
public class Submission {
    @Id
    private Integer id;
    @OneToOne
    private Problem problem;
    @OneToOne
    private User user;
    private String codeContent;
    private SubmissionStatus status;
    private Integer timeUsage;
    private Integer memoryUsage;
    private String language;
    private Integer codeLength;
    private Date datetime;
}
