package pl.mkowsky.jirawannabedemo.services;

import pl.mkowsky.jirawannabedemo.dictionary.EState;
import pl.mkowsky.jirawannabedemo.dto.TaskDTO;
import pl.mkowsky.jirawannabedemo.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAllTasks();
    void save(Task task);
    void remove(Task task);


    Task getTaskById(Long id);
    void createNewTask(TaskDTO taskDTO);
    void deleteTask(Long taskID);

    void changeTaskState(Long taskID, EState newState);


    List<Task> getAllProjectTasks(Long projectID);
}
