package pl.mkowsky.jirawannabedemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mkowsky.jirawannabedemo.dictionary.EChange;
import pl.mkowsky.jirawannabedemo.model.Task;
import pl.mkowsky.jirawannabedemo.model.TaskStatusChange;
import pl.mkowsky.jirawannabedemo.repository.TaskStatusRepository;

import java.time.ZoneId;
import java.util.Date;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {


    private TaskStatusRepository taskStatusRepository;

    @Autowired
    public TaskStatusServiceImpl(TaskStatusRepository taskStatusRepository) {
        this.taskStatusRepository = taskStatusRepository;
    }

    @Override
    public void newTaskCreated(Task task) {
        TaskStatusChange taskStatusChange = new TaskStatusChange(EChange.TASK_CREATED,
                "Task created.",
                new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                task);
        taskStatusRepository.save(taskStatusChange);

    }
}
