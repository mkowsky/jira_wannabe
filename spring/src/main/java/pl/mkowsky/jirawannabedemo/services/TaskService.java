package pl.mkowsky.jirawannabedemo.services;

import pl.mkowsky.jirawannabedemo.dictionary.EState;
import pl.mkowsky.jirawannabedemo.dto.BasicTaskInfoDTO;
import pl.mkowsky.jirawannabedemo.dto.TaskDTO;
import pl.mkowsky.jirawannabedemo.model.Task;

import java.util.Date;
import java.util.List;

public interface TaskService {

    List<Task> findAllTasks();
    void save(Task task);
    void remove(Task task);


    Task getTaskById(Long id);
    void createNewTask(TaskDTO taskDTO);
    void deleteTask(Long taskID);

    void changeTaskState(Long taskID, EState newState);
    void changeTaskUser(Long taskID, Long newUserID, Long previousTaskUser);
    void changeTaskDeadline(Long taskID, String newDeadline);

    List<Task> getAllProjectTasks(Long projectID);
    List<BasicTaskInfoDTO> getBasicTaskInfo(Long userID);
    List<BasicTaskInfoDTO> getBasicTaskInfoForProjectWithProjectID(Long projectID);
    List<BasicTaskInfoDTO> getBasicTaskInfo();
    List<Long[]> getTaksLength();
    List<Long[]> getTaksLength(Long userID);
    List<Long[]> getTasksLengthForProjectWithProjectID(Long projectID);
}
