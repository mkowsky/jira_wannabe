package pl.mkowsky.jirawannabedemo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
public class TaskStatusDTO {


    private BigInteger id;
    private String changeDescription;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime changeDate;
    private String  changeType;
    private  BigInteger taskID;
    private String taskName;
    private String taskKEY;
    private String pictureURL;


    public TaskStatusDTO(){

    }

    public TaskStatusDTO(BigInteger id, String changeDescription, Timestamp changeDate, String changeType, BigInteger taskID, String taskName, String taskKEY,
                         String pictureURL) {
        this.id = id;
        this.changeDescription = changeDescription;
        this.changeDate =   changeDate.toLocalDateTime();
        this.changeType = changeType;
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskKEY =  taskKEY;
        this.pictureURL = pictureURL;
    }

    public String getTaskKEY() {
        return taskKEY;
    }

    public void setTaskKEY(String taskKEY) {
        this.taskKEY = taskKEY;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public LocalDateTime getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Timestamp changeDate) {
        this.changeDate = changeDate.toLocalDateTime();
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getTaskID() {
        return taskID;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public void setTaskID(BigInteger taskID) {
        this.taskID = taskID;
    }

    public String getChangeDescription() {
        return changeDescription;
    }

    public void setChangeDescription(String changeDescription) {
        this.changeDescription = changeDescription;
    }



    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
