package pl.mkowsky.jirawannabedemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectName;

    @OneToMany(mappedBy = "project")
    @JsonBackReference
    private List<Task> taskList;



    @ManyToMany
    @JoinTable(name = "projects_users",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> projectUsers = new ArrayList<>();

    @OneToOne
    private User projectManager;


    public void addUser(User user){
        projectUsers.add(user);
    }

    public void removeUser(User user){
        projectUsers.remove(user);
    }


    public Project(){
    }

    public Project(String projectName, List<Task> taskList, List<User> projectUsers, User projectManager) {
        this.projectName = projectName;
        this.taskList = taskList;
        this.projectUsers = projectUsers;
        this.projectManager = projectManager;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<User> getProjectUsers() {
        return projectUsers;
    }

    public void setProjectUsers(List<User> projectUsers) {
        this.projectUsers = projectUsers;
    }

    public User getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
    }
}
