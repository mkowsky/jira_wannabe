package pl.mkowsky.jirawannabedemo.services;


import pl.mkowsky.jirawannabedemo.dictionary.EState;
import pl.mkowsky.jirawannabedemo.dto.TaskStatusDTO;
import pl.mkowsky.jirawannabedemo.model.*;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskStatusService {

    void newTaskCreated(Task task);
    void taskStatusChanged(EState previousState, EState newState, Task task);
    void taskUserChanged(User user, Task task, User previousUser);
    void newTaskIssue(Issue issue, Task task);
    void taskDeadlineChanged(Task task, LocalDateTime previousDeadline, String newDeadline);
    void newCommentForTask(Task task, Comment comment);
    List<TaskStatusChange> getAllTaskChangesForProject(Long projectID);
    List<TaskStatusDTO> testQuery(Long projectID);


}
