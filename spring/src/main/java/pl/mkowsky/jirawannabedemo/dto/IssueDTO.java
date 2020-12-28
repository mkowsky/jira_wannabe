package pl.mkowsky.jirawannabedemo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class IssueDTO {

    private Long issueID;
    private String issueDescription;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime issueDate;
    private String issueType;
    private String taskKEY;
    private String issueKEY;
    private Long reportedByID;
    private String issuePriority;
    private String issueStatus;
    private String userFullName;
    private String pictureURL;

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public Long getIssueID() {
        return issueID;
    }

    public void setIssueID(BigInteger issueID) {
        this.issueID = issueID.longValue();
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Timestamp issueDate) {
        this.issueDate = issueDate.toLocalDateTime();
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getTaskKEY() {
        return taskKEY;
    }

    public void setTaskKEY(String taskKEY) {
        this.taskKEY = taskKEY;
    }


    public Long getReportedByID() {
        return reportedByID;
    }

    public void setReportedByID(BigInteger reportedByID) {
        this.reportedByID = reportedByID.longValue();
    }

    public String getIssuePriority() {
        return issuePriority;
    }

    public void setIssuePriority(String issuePriority) {
        this.issuePriority = issuePriority;
    }

    public String getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getIssueKEY() {
        return issueKEY;
    }

    public void setIssueKEY(String issueKEY) {
        this.issueKEY = issueKEY;
    }
}
