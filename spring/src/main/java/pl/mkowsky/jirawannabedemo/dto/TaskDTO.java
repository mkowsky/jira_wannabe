package pl.mkowsky.jirawannabedemo.dto;

import pl.mkowsky.jirawannabedemo.dictionary.EDepartment;
import pl.mkowsky.jirawannabedemo.dictionary.EPriority;
import pl.mkowsky.jirawannabedemo.dictionary.EState;


import java.util.Date;

public class TaskDTO {

    private Long taskManagerID;
    private EPriority taskPriority;
    private EDepartment department;
    private String taskTitle;
    private String taskDescription;
    private EState state;
    private Date taskDeadline;
    private Long taskUser;
    private Long projectID;

    public TaskDTO(){

    }

    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long projectID) {
        this.projectID = projectID;
    }



    public Long getTaskManagerID() {
        return taskManagerID;
    }

    public void setTaskManagerID(Long taskManagerID) {
        this.taskManagerID = taskManagerID;
    }

    public EPriority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(EPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public EDepartment getDepartment() {
        return department;
    }

    public void setDepartment(EDepartment department) {
        this.department = department;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public EState getState() {
        return state;
    }

    public void setState(EState state) {
        this.state = state;
    }

    public Date getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(Date taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public Long getTaskUser() {
        return taskUser;
    }

    public void setTaskUser(Long taskUser) {
        this.taskUser = taskUser;
    }
}
