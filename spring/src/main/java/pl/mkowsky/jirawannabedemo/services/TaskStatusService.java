package pl.mkowsky.jirawannabedemo.services;

import pl.mkowsky.jirawannabedemo.dictionary.EState;
import pl.mkowsky.jirawannabedemo.model.Task;

public interface TaskStatusService {

    void newTaskCreated(Task task);
    void taskStatusChanged(EState previousState, EState newState, Task task);
}
