package xyz.chengzi.ooad.entity;

import xyz.chengzi.ooad.embeddable.SubmissionCase;
import xyz.chengzi.ooad.embeddable.SubmissionStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "submissions")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Problem problem;
    @OneToOne
    private User user;
    private SubmissionStatus status;
    @ElementCollection
    private List<SubmissionCase> cases;
    private String language;
    private Integer codeLength;
    private Date timestamp;

    public Integer getId() {
        return id;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(SubmissionStatus status) {
        this.status = status;
    }

    public List<SubmissionCase> getCases() {
        return cases;
    }

    public void setCases(List<SubmissionCase> cases) {
        this.cases = cases;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(Integer codeLength) {
        this.codeLength = codeLength;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
