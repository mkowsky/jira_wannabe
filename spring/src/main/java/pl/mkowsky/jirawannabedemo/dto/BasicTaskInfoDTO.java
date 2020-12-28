package pl.mkowsky.jirawannabedemo.dto;

import java.math.BigInteger;

public class BasicTaskInfoDTO {

    private Long taskID;
    private Long userID;
    private String projectName;
    private String userFullName;
    private String taskName;
    private String taskState;
    private String taskPriority;
    private String userPictureURL;


    public Long getTaskID() {
        return taskID;
    }

    public void setTaskID(BigInteger taskID) {
        this.taskID = taskID.longValue();
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(BigInteger userID) {
        this.userID = userID.longValue();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getUserPictureURL() {
        return userPictureURL;
    }

    public void setUserPictureURL(String userPictureURL) {
        this.userPictureURL = userPictureURL;
    }
}
