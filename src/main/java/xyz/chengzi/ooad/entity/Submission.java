package xyz.chengzi.ooad.entity;

import org.jetbrains.annotations.NotNull;
import xyz.chengzi.ooad.embeddable.SubmissionCase;
import xyz.chengzi.ooad.embeddable.SubmissionStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "submissions")
public class Submission implements Comparable<Submission> {
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
    private Integer timeUsage;
    private Integer memoryUsage;
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

    public Integer getTimeUsage() {
        return timeUsage;
    }

    public void setTimeUsage(Integer timeUsage) {
        this.timeUsage = timeUsage;
    }

    public Integer getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(Integer memoryUsage) {
        this.memoryUsage = memoryUsage;
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

    @Override
    public int compareTo(@NotNull Submission another) {
        if (getTimeUsage().equals(another.getTimeUsage())) {
            return getMemoryUsage().compareTo(another.getMemoryUsage());
        }
        return getTimeUsage().compareTo(another.getTimeUsage());
    }
}
