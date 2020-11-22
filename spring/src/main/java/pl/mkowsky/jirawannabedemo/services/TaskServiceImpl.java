package pl.mkowsky.jirawannabedemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mkowsky.jirawannabedemo.dictionary.EState;
import pl.mkowsky.jirawannabedemo.dto.TaskDTO;
import pl.mkowsky.jirawannabedemo.model.Task;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.repository.TaskRepository;
import pl.mkowsky.jirawannabedemo.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserService userService;
    private UserRepository userRepository;
    private TaskStatusService taskStatusService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserService userService, UserRepository userRepository, TaskStatusService taskStatusService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.taskStatusService = taskStatusService;
    }

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void remove(Task task) {
        taskRepository.delete(task);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.getTaskById(id);
    }

    @Override
    public void createNewTask(TaskDTO taskDTO) {
        List<User> taskUsers = new ArrayList<>();
        for (int i = 0; i < taskDTO.getTaskUsers().length; i++) {
            taskUsers.add(userRepository.findUserById(taskDTO.getTaskUsers()[i]));
        }

        Task newTask = new Task(generateTaskID(),
                taskDTO.getTaskTitle(),
                new Date(),
                taskDTO.getTaskDeadline(),
                taskDTO.getTaskDescription(),
                taskDTO.getState(),
                userService.getUserById(taskDTO.getTaskManagerID()),
                null,
                taskDTO.getDepartment(),
                taskDTO.getTaskPriority()
        );


        save(newTask);
        taskStatusService.newTaskCreated(newTask);


        for (int j = 0; j < taskUsers.size(); j++) {
            taskUsers.get(j).addTask(newTask);
            userService.save(taskUsers.get(j));
        }


    }

    @Override
    public void deleteTask(Long taskID) {
        Task task = taskRepository.getTaskById(taskID);
        for (int i = 0; i < task.getUsers().size(); i++) {
            task.getUsers().get(i).removeTask(task);
        }

        remove(task);
    }

    @Override
    public void changeTaskState(Long taskID, EState newState) {
        Task task = taskRepository.getTaskById(taskID);
        taskStatusService.taskStatusChanged(task.getState(), newState, task);
        task.setState(newState);
        taskRepository.save(task);
    }

    String generateTaskID() {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder stringBuilder = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            stringBuilder.append(AlphaNumericString.charAt(index));
        }
        return stringBuilder.toString();
    }
}
