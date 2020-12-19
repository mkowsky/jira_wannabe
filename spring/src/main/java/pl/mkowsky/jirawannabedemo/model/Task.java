package pl.mkowsky.jirawannabedemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.mkowsky.jirawannabedemo.dictionary.EDepartment;
import pl.mkowsky.jirawannabedemo.dictionary.EPriority;
import pl.mkowsky.jirawannabedemo.dictionary.EState;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @JsonIgnore
    private User taskManager;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
//    @ManyToMany(mappedBy = "userstasks")
//    private List<User> users = new ArrayList<User>();

    @Enumerated(EnumType.STRING)
    private EDepartment department;

    @Enumerated(EnumType.STRING)
    private EPriority taskPriority;

    @OneToMany(mappedBy = "task",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    //@JoinColumn(name = "task_id")
    private List<Comment> comments;

    @OneToMany(mappedBy = "task",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    //@JoinColumn(name = "task_id")
    private List<TaskStatusChange> taskChanges;


    @OneToMany(mappedBy = "task",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Issue> taskIssues;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

    public Task() {

    }

    public Task(String taskID, String name, Date createdDate, Date expireDate, String description, EState state, User taskManager, User users
            , EDepartment department, EPriority taskPriority, Project project) {
        this.taskID = taskID;
        this.name = name;
        this.createdDate = createdDate;
        this.expireDate = expireDate;
        this.description = description;
        this.state = state;
        this.taskManager = taskManager;
        this.user = users;
        this.department = department;
        this.taskPriority = taskPriority;
        this.project = project;
    }


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<TaskStatusChange> getTaskChanges() {
        return taskChanges;
    }

    public void setTaskChanges(List<TaskStatusChange> taskChanges) {
        this.taskChanges = taskChanges;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeTask(Comment comment) {
        comments.remove(comment);
    }

    public User getTaskManager() {
        return taskManager;
    }

    public void setTaskManager(User taskManager) {
        this.taskManager = taskManager;
    }

//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public EDepartment getDepartment() {
        return department;
    }

    public void setDepartment(EDepartment department) {
        this.department = department;
    }

    public EPriority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(EPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
