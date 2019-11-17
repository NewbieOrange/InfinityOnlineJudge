package xyz.chengzi.ooad.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "problems")
public class Problem {
    @Id
    private Integer id;
    private String name;
    private Integer acceptedAmount;
    private Integer submissionAmount;
}
