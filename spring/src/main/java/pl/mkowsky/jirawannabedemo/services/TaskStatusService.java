package pl.mkowsky.jirawannabedemo.services;

import pl.mkowsky.jirawannabedemo.model.Task;

public interface TaskStatusService {

    void newTaskCreated(Task task);
}
