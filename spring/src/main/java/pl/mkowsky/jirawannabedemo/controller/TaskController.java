package pl.mkowsky.jirawannabedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mkowsky.jirawannabedemo.TaskDTO;
import pl.mkowsky.jirawannabedemo.model.Task;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.services.TaskService;
import pl.mkowsky.jirawannabedemo.services.UserService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;
    private UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService){
        this.taskService = taskService;
        this.userService = userService;
    }

    @PostMapping(value = "/create-new")
    private void createNewTask(@RequestBody TaskDTO taskDTO){
        System.out.println("New task creation");
        System.out.println(taskDTO.getTaskManagerID());
        System.out.println(taskDTO.getName());
        System.out.println(taskDTO.getDescription());
        taskService.createNewTask(taskDTO);
    }

    @GetMapping(value = "/list-all")
    private List<Task> listAllTasks(){
        return taskService.findAllTasks();
    }

    @GetMapping(value="/user/{userID}/to-task/{taskID}")
    private void addUserToTask(@PathVariable("userID") Long userID,
                               @PathVariable("taskID") Long taskID){
        User user = userService.getUserById(userID);
        Task task = taskService.getTaskById(taskID);

        System.out.println("User --->" +user.getUsername());
        System.out.println("Task --->" +task.getDescription());

        System.out.println("Procedura dodawania rozpoczeta: ");
        user.addTask(task);
        userService.save(user);
    }

}
