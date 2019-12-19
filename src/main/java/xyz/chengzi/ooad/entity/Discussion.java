package xyz.chengzi.ooad.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Discussion parent;

    @OneToMany(mappedBy = "parent")
    private List<Discussion> children;

    @Column(columnDefinition = "clob")
    private String comment;

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

    public Discussion getParent() {
        return parent;
    }

    public void setParent(Discussion parent) {
        this.parent = parent;
    }

    public List<Discussion> getChildren() {
        return children;
    }

    public void setChildren(List<Discussion> children) {
        this.children = children;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
