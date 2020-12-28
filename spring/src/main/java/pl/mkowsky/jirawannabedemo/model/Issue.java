package pl.mkowsky.jirawannabedemo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import pl.mkowsky.jirawannabedemo.dictionary.EIssue;
import pl.mkowsky.jirawannabedemo.dictionary.EPriority;
import pl.mkowsky.jirawannabedemo.dictionary.EStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String issueKEY;

    @Enumerated(EnumType.STRING)
    private EStatus issueStatus;

    @Enumerated(EnumType.STRING)
    private EPriority issuePriority;

    private String description;

    @Enumerated(EnumType.STRING)
    private EIssue issueType;

    @ManyToOne
    @JsonIgnore
    private Task task;

    @ManyToOne
    private User reportedBy;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime issueDate;

    public Issue(){
    }

    public Issue(String description, EIssue issueType, Task task, LocalDateTime issueDate) {
        this.description = description;
        this.issueType = issueType;
        this.task = task;
        this.issueDate = issueDate;
    }

    public Issue(String issueKEY, EStatus issueStatus, EPriority issuePriority, String description, EIssue issueType, Task task, LocalDateTime issueDate, User reportedBy) {
        this.issueKEY = issueKEY;
        this.issueStatus = issueStatus;
        this.issuePriority = issuePriority;
        this.description = description;
        this.issueType = issueType;
        this.task = task;
        this.issueDate = issueDate;
        this.reportedBy = reportedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public EIssue getIssueType() {
        return issueType;
    }

    public void setIssueType(EIssue issueType) {
        this.issueType = issueType;
    }

    public String getIssueKEY() {
        return issueKEY;
    }

    public void setIssueKEY(String issueKEY) {
        this.issueKEY = issueKEY;
    }

    public EStatus getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(EStatus issueStatus) {
        this.issueStatus = issueStatus;
    }

    public EPriority getIssuePriority() {
        return issuePriority;
    }

    public void setIssuePriority(EPriority issuePriority) {
        this.issuePriority = issuePriority;
    }

    public User getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(User reportedBy) {
        this.reportedBy = reportedBy;
    }
}
