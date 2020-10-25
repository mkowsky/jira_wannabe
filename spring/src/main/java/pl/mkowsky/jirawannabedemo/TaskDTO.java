package pl.mkowsky.jirawannabedemo;

public class TaskDTO {

    private Long taskManagerID;
    private String name;
    private String description;


    public TaskDTO(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTaskManagerID() {
        return taskManagerID;
    }

    public void setTaskManagerID(Long taskManagerID) {
        this.taskManagerID = taskManagerID;
    }
}
