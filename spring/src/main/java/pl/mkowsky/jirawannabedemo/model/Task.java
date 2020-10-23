package pl.mkowsky.jirawannabedemo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String taskID;

    private String name;

    private Date createdDate;

    private Date expireDate;

    private String description;

    private EState state;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User taskManager;

    @ManyToMany(mappedBy = "currentTasks")
    private List<User> users = new ArrayList<>();

    public Task(){

    }

    public User getTaskManager() {
        return taskManager;
    }

    public void setTaskManager(User taskManager) {
        this.taskManager = taskManager;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EState getState() {
        return state;
    }

    public void setState(EState state) {
        this.state = state;
    }



}
