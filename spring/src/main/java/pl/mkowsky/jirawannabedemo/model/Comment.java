package pl.mkowsky.jirawannabedemo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String comment;

    @Column(nullable=false)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime commentDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonIgnore
    private Task task;


    public Comment() {

    }

    public Comment(String comment, LocalDateTime commentDate, User user) {
        this.comment = comment;
        this.commentDate = commentDate;
        this.user = user;
    }

    public Comment(String comment, LocalDateTime commentDate, User user, Task task) {
        this.comment = comment;
        this.commentDate = commentDate;
        this.user = user;
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
