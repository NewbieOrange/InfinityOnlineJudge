package xyz.chengzi.ooad.entity;

import xyz.chengzi.ooad.embeddable.ContestStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contests")
public class Contest {
    @Id
    private Integer id;
    private String title;
    private String description;
    @OneToMany
    private List<Problem> problems;
    private Date startDate, endDate;
    private ContestStatus status;
}
