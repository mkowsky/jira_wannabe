package pl.mkowsky.jirawannabedemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import pl.mkowsky.jirawannabedemo.dictionary.ERole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String username;

    private String email;

    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @JsonIgnore
    @OneToMany(mappedBy = "taskManager")
    private List<Task> tasks;

    @JsonIgnore
    @OneToMany(mappedBy="user")
    private List<Task> userTasks;

    public List<Task> getUserTasks() {
        return userTasks;
    }

    public void setUserTasks(List<Task> userTasks) {
        this.userTasks = userTasks;
    }

    //    @JsonIgnore
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "users_tasks",
//            joinColumns = {@JoinColumn(name = "user_id")},
//            inverseJoinColumns = {@JoinColumn(name = "task_id")})
//    private List<Task> userstasks = new ArrayList<Task>();


    @JsonIgnore
    @ManyToMany(mappedBy = "projectUsers")
    private List<Project> projects = new ArrayList<Project>();


    private String firstName;
    private String lastName;

    private String pictureURL;

    private boolean activated;

    public User(String email, String password, Set<Role> roles, String firstName, String lastName, boolean activated, String pictureURL){
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.activated = activated;
        this.pictureURL = pictureURL;
    }


    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public User() {

    }

    public User(String email, String password) {
//        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String firstName, String lastName) {
//        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
//
//    public List<Task> getUserstasks() {
//        return userstasks;
//    }
//
//    public void setUserstasks(List<Task> userstasks) {
//        this.userstasks = userstasks;
//    }

    //    public void addTask(Task task) {
//        userstasks.add(task);
//        //task.getUsers().add(this);
//    }
//
//    public void removeTask(Task task) {
//        userstasks.remove(task);
//        //task.getUsers().remove(this);
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public boolean checkIfProjectManager(){
        for(Role role : roles){
            if (role.getName().equals(ERole.ROLE_PROJECT_MANAGER)) return true;
        }
        return false;
    }
}
