package pl.mkowsky.jirawannabedemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskID;

    private String name;

    private Date createdDate;

    private Date expireDate;

    private String description;

    @Enumerated(EnumType.STRING)
    private EState state;


    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User taskManager;



    @ManyToMany(mappedBy = "userstasks")
    private List<User> users = new ArrayList<User>();

    public Task() {

    }

    public Task(String taskID, String name, Date createdDate, Date expireDate, String description, EState state, User taskManager, List<User> users) {
        this.taskID = taskID;
        this.name = name;
        this.createdDate = createdDate;
        this.expireDate = expireDate;
        this.description = description;
        this.state = state;
        this.taskManager = taskManager;
        this.users = users;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
