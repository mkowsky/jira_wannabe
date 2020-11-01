package pl.mkowsky.jirawannabedemo.dto;

import pl.mkowsky.jirawannabedemo.dictionary.EDepartment;
import pl.mkowsky.jirawannabedemo.dictionary.EState;


import java.util.Date;
import java.util.List;

public class TaskDTO {

    private Long taskManagerID;
    private int taskPriority;
    private EDepartment department;
    private String taskTitle;
    private String taskDescription;
    private EState state;
    private Date taskDeadline;
    private Long[] taskUsers;


    public TaskDTO(){

    }

    public Long getTaskManagerID() {
        return taskManagerID;
    }

    public void setTaskManagerID(Long taskManagerID) {
        this.taskManagerID = taskManagerID;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
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

    public Long[] getTaskUsers() {
        return taskUsers;
    }

    public void setTaskUsers(Long[] taskUsers) {
        this.taskUsers = taskUsers;
    }
}
