package xyz.chengzi.ooad.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class DiscussionComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private DiscussionThread thread;

    @ManyToOne(cascade = CascadeType.ALL)
    private DiscussionComment parent;

    @OneToMany(mappedBy = "parent")
    private List<DiscussionComment> children;

    private String content;

    private Date timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DiscussionThread getThread() {
        return thread;
    }

    public void setThread(DiscussionThread thread) {
        this.thread = thread;
    }

    public DiscussionComment getParent() {
        return parent;
    }

    public void setParent(DiscussionComment parent) {
        this.parent = parent;
    }

    public List<DiscussionComment> getChildren() {
        return children;
    }

    public void setChildren(List<DiscussionComment> children) {
        this.children = children;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String comment) {
        this.content = comment;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
