package pl.mkowsky.jirawannabedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mkowsky.jirawannabedemo.dictionary.EIssue;
import pl.mkowsky.jirawannabedemo.dictionary.EPriority;
import pl.mkowsky.jirawannabedemo.dictionary.EState;
import pl.mkowsky.jirawannabedemo.dto.BasicTaskInfoDTO;
import pl.mkowsky.jirawannabedemo.dto.PersonalDataDTO;
import pl.mkowsky.jirawannabedemo.dto.TaskDTO;
import pl.mkowsky.jirawannabedemo.model.Task;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.services.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;
    private UserService userService;
    private IssueService issueService;
    private TaskStatusService taskStatusService;
    private ProjectService projectService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService, IssueService issueService, TaskStatusService taskStatusService, ProjectService projectService) {
        this.taskService = taskService;
        this.userService = userService;
        this.issueService = issueService;
        this.taskStatusService = taskStatusService;
        this.projectService = projectService;
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
        taskService.changeTaskState(taskID, newState);
        return ResponseEntity.ok("Status changed");
    }

    @PostMapping(value = "/change-task-person")
    private ResponseEntity<?> changeTaskPerson(@RequestParam("taskID") Long taskID,
                                               @RequestParam("newTaskUser") Long newTaskUserID,
                                                @RequestParam("previousTaskUser") Long previousTaskUser) {
        taskService.changeTaskUser(taskID, newTaskUserID, previousTaskUser);
        return ResponseEntity.ok("Task user changed");
    }

    @PostMapping(value = "/change-task-deadline")
    private ResponseEntity<?> changeTaskDeadline(@RequestParam("taskID") Long taskID,
                                                 @RequestParam("newTaskDeadline")String newDeadline){
        taskService.changeTaskDeadline(taskID, newDeadline);
        return ResponseEntity.ok("Task deadline changed.");
    }

    @PostMapping(value = "/report-new-issue-for-task")
    private ResponseEntity<?> reportNewIssueForTask(@RequestParam("taskID") Long taskID,
                                                    @RequestParam("issueDescription") String issueDescription,
                                                    @RequestParam("issueType") EIssue issueType,
                                                    @RequestParam("issuePriority")EPriority issuePriority,
                                                    @RequestParam("reportingUserID") Long reportingUserID) {
        issueService.newIssueReported(taskID, issueDescription, issueType, issuePriority, reportingUserID);
        return ResponseEntity.ok("Issue has been reported.");
    }

//    @GetMapping(value = "/get-all-task-changes-for-project/{projectID}")
//    private List<?> getAllTaskChangesForProject(@PathVariable("projectID") Long projectID) {
//        return taskStatusService.getAllTaskChangesForProject(projectID);
//    }




    @GetMapping(value = "/get-basic-tasks-info/{userID}")
    private List<BasicTaskInfoDTO> getBasicTaskInfo(@PathVariable("userID") Long userID) {
        User user = userService.getUserById(userID);
        if (user.checkIfProjectManager()) {
            return taskService.getBasicTaskInfo();
        } else {
            return taskService.getBasicTaskInfo(userID);
        }
    }
    @GetMapping(value = "/test/{userID}/{projectID}")
    private List<BasicTaskInfoDTO> getBasicTasksInfoForUserInProject(@PathVariable("userID") Long userID, @PathVariable("projectID") Long projectID){
        return taskService.getBasicTaskInfoForUserInProject(userID, projectID);
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
    private int getNumberOfTasksByTheirStatusForProjectWitID(@PathVariable("projectID") Long projectID) {
        int maxLength = 0;
        for (int i = 0; i < taskService.getTasksLengthForProjectWithProjectID(projectID).get(0).length; i++) {
            if (taskService.getTasksLengthForProjectWithProjectID(projectID).get(0)[i] > maxLength) {
                maxLength = taskService.getTasksLengthForProjectWithProjectID(projectID).get(0)[i].intValue();
            }
        }
        return maxLength;
    }

    @GetMapping(value = "/get-all-project-members/{taskID}")
    List<PersonalDataDTO> getAllProjectMembersBasedOnTaskID(@PathVariable("taskID") Long taskID) {
        Task task = taskService.getTaskById(taskID);
        List<User> users = task.getProject().getProjectUsers();
        List<PersonalDataDTO> projectUsers = new ArrayList<>();
        for (User user : users) {
            projectUsers.add(new PersonalDataDTO(user.getId(), user.getFirstName() + " " + user.getLastName()));
        }

        return projectUsers;
    }

    @GetMapping(value = "/get-basic-info-for-all-users-tasks/{userID}")
    List<BasicTaskInfoDTO> getBasicInfoForAllUsersTasks(@PathVariable ("userID") Long userID){
        return null;
    }

    @GetMapping(value = "/get-all-task-changes-for-project/{projectID}")
    private List<?> getAllTaskChangesForProject(@PathVariable("projectID") Long projectID) {
        return taskStatusService.testQuery(projectID);
    }
}
