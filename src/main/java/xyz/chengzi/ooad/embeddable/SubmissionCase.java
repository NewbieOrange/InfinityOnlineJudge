package xyz.chengzi.ooad.embeddable;

import javax.persistence.Embeddable;

@Embeddable
public class SubmissionCase {
    private Integer id;
    private SubmissionStatus status;
    private Integer timeUsage;
    private Integer memoryUsage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(SubmissionStatus status) {
        this.status = status;
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
}
