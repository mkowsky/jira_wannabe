package pl.mkowsky.jirawannabedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mkowsky.jirawannabedemo.dictionary.EState;
import pl.mkowsky.jirawannabedemo.dto.BasicTaskInfoDTO;
import pl.mkowsky.jirawannabedemo.dto.TaskDTO;
import pl.mkowsky.jirawannabedemo.model.Task;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.services.IssueService;
import pl.mkowsky.jirawannabedemo.services.TaskService;
import pl.mkowsky.jirawannabedemo.services.TaskStatusService;
import pl.mkowsky.jirawannabedemo.services.UserService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;
    private UserService userService;
    private IssueService issueService;
    private TaskStatusService taskStatusService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService, IssueService issueService, TaskStatusService taskStatusService) {
        this.taskService = taskService;
        this.userService = userService;
        this.issueService = issueService;
        this.taskStatusService = taskStatusService;
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
    private List<Task> getUsersTasks(@PathVariable("userID") Long userID) {
        User user = userService.getUserById(userID);


        return user.getUserTasks();
    }





    @GetMapping(value = "/user/{userID}/to-task/{taskID}")
    private void addUserToTask(@PathVariable("userID") Long userID,
                               @PathVariable("taskID") Long taskID) {
        User user = userService.getUserById(userID);
        Task task = taskService.getTaskById(taskID);

        System.out.println("User --->" + user.getUsername());
        System.out.println("Task --->" + task.getDescription());

        System.out.println("Procedura dodawania rozpoczeta: ");
        //TODO: tutaj
        //user.addTask(task);
        userService.save(user);
    }

    @PostMapping(value = "/change-task-state")
    private ResponseEntity<?> changeTaskState(@RequestParam("taskID") Long taskID,
                                              @RequestParam("newState") EState newState) {
        System.out.println(taskID);
        System.out.println(newState);
        taskService.changeTaskState(taskID, newState);

        return ResponseEntity.ok("Status changed");
    }

    @PostMapping(value = "/change-task-person")
    private ResponseEntity<?> changeTaskPerson(@RequestParam("taskID") Long taskID, @RequestParam("newTaskUser") Long newTaskUserID) {
        taskService.changeTaskUser(taskID, newTaskUserID);
        return ResponseEntity.ok("Task user changed");
    }

    @PostMapping(value = "/report-new-issue-for-task")
    private ResponseEntity<?> reportNewIssueForTask(@RequestParam("taskID") Long taskID, @RequestParam("issueDescription") String issueDescription) {
        issueService.newIssueReported(taskID, issueDescription);
        return ResponseEntity.ok("Issue has been reported.");
    }

//    @GetMapping(value = "/get-all-task-changes-for-project/{projectID}")
//    private List<?> getAllTaskChangesForProject(@PathVariable("projectID") Long projectID) {
//        return taskStatusService.getAllTaskChangesForProject(projectID);
//    }

    @GetMapping(value = "/get-all-task-changes-for-project/{projectID}")
    private List<?> getAllTaskChangesForProject(@PathVariable("projectID") Long projectID) {
        return taskStatusService.testQuery(projectID);
    }


    @GetMapping(value = "/get-basic-tasks-info/{userID}")
    private List<BasicTaskInfoDTO> getBasicTaskInfo(@PathVariable("userID") Long userID) {
        User user = userService.getUserById(userID);
        if(user.checkIfProjectManager()){
            return taskService.getBasicTaskInfo();
        } else {
            return taskService.getBasicTaskInfo(userID);
        }
    }

    @GetMapping(value = "/list-all-tasks-in-project/{projectID}")
    List<BasicTaskInfoDTO> getAllProjectTasks(@PathVariable("projectID") Long projectID) {
        return taskService.getBasicTaskInfoForProjectWithProjectID(projectID);
    }

    @GetMapping(value = "/get-number-of-tasks-by-their-status")
    private int getNumberOfTaskByTheirStatus(@RequestParam("userID") Long userID) {
        int maxLength = 0;
        User user = userService.getUserById(userID);
        if (user.checkIfProjectManager()) {
            for (int i = 0; i < taskService.getTaksLength().get(0).length; i++) {
                if (taskService.getTaksLength().get(0)[i] > maxLength) {
                    maxLength = taskService.getTaksLength().get(0)[i].intValue();
                }
            }
        } else {
            for (int i = 0; i < taskService.getTaksLength(userID).get(0).length; i++) {
                if (taskService.getTaksLength(userID).get(0)[i] > maxLength) {
                    maxLength = taskService.getTaksLength(userID).get(0)[i].intValue();
                }
            }
        }
        return maxLength;
    }
    @GetMapping(value = "/get-number-of-tasks-by-their-status-for-project/{projectID}")
    private int getNumberOfTasksByTheirStatusForProjectWitID(@PathVariable("projectID") Long projectID){
        int maxLength = 0;
        for(int i =0; i < taskService.getTasksLengthForProjectWithProjectID(projectID).get(0).length; i++){
            if(taskService.getTasksLengthForProjectWithProjectID(projectID).get(0)[i] > maxLength){
                maxLength = taskService.getTasksLengthForProjectWithProjectID(projectID).get(0)[i].intValue();
            }
        }
        return maxLength;
    }


}
