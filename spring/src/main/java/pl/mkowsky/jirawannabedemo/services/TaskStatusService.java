package pl.mkowsky.jirawannabedemo.services;


import pl.mkowsky.jirawannabedemo.dictionary.EState;
import pl.mkowsky.jirawannabedemo.dto.TaskStatusDTO;
import pl.mkowsky.jirawannabedemo.model.Task;
import pl.mkowsky.jirawannabedemo.model.TaskStatusChange;
import pl.mkowsky.jirawannabedemo.model.User;

import java.util.List;

public interface TaskStatusService {

    void newTaskCreated(Task task);
    void taskStatusChanged(EState previousState, EState newState, Task task);
    void taskUserChanged(User user, Task task);
    void newTaskIssue(String issueDescription, Task task);

    List<TaskStatusChange> getAllTaskChangesForProject(Long projectID);
    List<TaskStatusDTO> testQuery(Long projectID);
}
