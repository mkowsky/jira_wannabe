package pl.mkowsky.jirawannabedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mkowsky.jirawannabedemo.dictionary.EState;
import pl.mkowsky.jirawannabedemo.dto.TaskDTO;
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
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping(value = "/list-all")
    private List<Task> listAllTasks() {
        return taskService.findAllTasks();
    }

    @PostMapping(value = "/create-new")
    private void createNewTask(@RequestBody TaskDTO taskDTO) {
        taskService.createNewTask(taskDTO);
    }

    @PostMapping(value = "/delete-task")
    private void deleteTask(@RequestParam Long taskID) {
        taskService.deleteTask(taskID);
    }

    @GetMapping(value = "get-task/{taskID}")
    private Task getTaskById(@PathVariable("taskID") Long taskID) {
        return taskService.getTaskById(taskID);
    }

    @GetMapping(value = "get-user-tasks/{userID}")
    private List<Task> getUsersTasks(@PathVariable("userID") Long userID){
        User user = userService.getUserById(userID);
        return user.getUserstasks();
    }


    @GetMapping(value = "/user/{userID}/to-task/{taskID}")
    private void addUserToTask(@PathVariable("userID") Long userID,
                               @PathVariable("taskID") Long taskID) {
        User user = userService.getUserById(userID);
        Task task = taskService.getTaskById(taskID);

        System.out.println("User --->" + user.getUsername());
        System.out.println("Task --->" + task.getDescription());

        System.out.println("Procedura dodawania rozpoczeta: ");
        user.addTask(task);
        userService.save(user);
    }

    @PostMapping(value= "/change-task-state")
    private ResponseEntity<?> changeTaskState(@RequestParam("taskID") Long taskID,
                                              @RequestParam("newState") EState newState){
        System.out.println(taskID);
        System.out.println(newState);
        taskService.changeTaskState(taskID, newState);

        return ResponseEntity.ok("Status changed");
    }

}
