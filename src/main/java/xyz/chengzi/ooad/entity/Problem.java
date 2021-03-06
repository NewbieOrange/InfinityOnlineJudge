package xyz.chengzi.ooad.entity;

import xyz.chengzi.ooad.embeddable.Visibility;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "problems")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @Column(columnDefinition = "clob")
    private String description;
    @Column(columnDefinition = "clob")
    private String descriptionHtml;
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<Submission> submissions;
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    @OrderBy(value = "timeUsage desc, memoryUsage desc")
    private Map<User, Submission> rankList;
    @OneToOne
    private DiscussionThread discussionThread;
    private Visibility visibility;
    private String type;
    private Boolean special;
    private Integer timeLimit;
    private Integer memoryLimit;
    private Integer acceptedAmount;
    private Integer submissionAmount;

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

    public String getDescriptionHtml() {
        return descriptionHtml;
    }

    public void setDescriptionHtml(String descriptionHtml) {
        this.descriptionHtml = descriptionHtml;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public Map<User, Submission> getRankList() {
        return rankList;
    }

    public void setRankList(Map<User, Submission> rankList) {
        this.rankList = rankList;
    }

    public DiscussionThread getDiscussionThread() {
        return discussionThread;
    }

    public void setDiscussionThread(DiscussionThread discussionThread) {
        this.discussionThread = discussionThread;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(Integer memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public Integer getAcceptedAmount() {
        return acceptedAmount;
    }

    public void setAcceptedAmount(Integer acceptedAmount) {
        this.acceptedAmount = acceptedAmount;
    }

    public Integer getSubmissionAmount() {
        return submissionAmount;
    }

    public void setSubmissionAmount(Integer submissionAmount) {
        this.submissionAmount = submissionAmount;
    }
}
