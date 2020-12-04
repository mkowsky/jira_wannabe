package pl.mkowsky.jirawannabedemo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import pl.mkowsky.jirawannabedemo.dictionary.EChange;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tasks_Changes")
public class TaskStatusChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    private EChange changeType;

    private String changeDescription;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime changeDate;

    @ManyToOne
    @JsonIgnore
    private Task task;





    public TaskStatusChange(){

    }

    public TaskStatusChange(EChange changeType, String changeDescription, LocalDateTime changeDate, Task task) {
        this.changeType = changeType;
        this.changeDescription = changeDescription;
        this.changeDate = changeDate;
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EChange getChangeType() {
        return changeType;
    }

    public void setChangeType(EChange changeType) {
        this.changeType = changeType;
    }

    public String getChangeDescription() {
        return changeDescription;
    }

    public void setChangeDescription(String changeDescription) {
        this.changeDescription = changeDescription;
    }

    public LocalDateTime getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(LocalDateTime changeDate) {
        this.changeDate = changeDate;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }



}
