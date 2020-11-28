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


    public void addUser(User user){
        projectUsers.add(user);
    }

    public void removeUser(User user){
        projectUsers.remove(user);
    }


    public Project(){
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
}
