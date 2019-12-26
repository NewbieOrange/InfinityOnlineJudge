package xyz.chengzi.ooad.entity;

import xyz.chengzi.ooad.embeddable.ContestStatus;
import xyz.chengzi.ooad.embeddable.Visibility;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contests")
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL)
    private List<ContestProblem> problems;
    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL)
    private List<ContestSubmission> submissions;
    private Date startDate, endDate;
    private ContestStatus status;
    private Visibility visibility;
    private String mode;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ContestProblem> getProblems() {
        return problems;
    }

    public List<ContestSubmission> getSubmissions() {
        return submissions;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ContestStatus getStatus() {
        return status;
    }

    public void setStatus(ContestStatus status) {
        this.status = status;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
