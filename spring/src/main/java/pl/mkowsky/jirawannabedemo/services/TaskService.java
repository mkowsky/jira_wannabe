package pl.mkowsky.jirawannabedemo.services;

import pl.mkowsky.jirawannabedemo.TaskDTO;
import pl.mkowsky.jirawannabedemo.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAllTasks();
    void save(Task task);
    void remove(Task task);
    void createNewTask(TaskDTO taskDTO);
    Task getTaskById(Long id);
}
