package pl.mkowsky.jirawannabedemo.services;

import org.hibernate.query.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mkowsky.jirawannabedemo.dictionary.EState;
import pl.mkowsky.jirawannabedemo.dto.BasicTaskInfoDTO;
import pl.mkowsky.jirawannabedemo.dto.TaskDTO;
import pl.mkowsky.jirawannabedemo.model.Task;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.repository.TaskRepository;
import pl.mkowsky.jirawannabedemo.repository.UserRepository;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserService userService;
    private UserRepository userRepository;
    private TaskStatusService taskStatusService;
    private EmailService emailService;
    private ProjectService projectService;
    private EntityManager entityManager;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserService userService, UserRepository userRepository, TaskStatusService taskStatusService, EmailService emailService,
    ProjectService projectService, EntityManager entityManager) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.taskStatusService = taskStatusService;
        this.emailService = emailService;
        this.projectService = projectService;
        this.entityManager = entityManager;
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
        User taskUser = userService.getUserById(taskDTO.getTaskUser());

        Task newTask = new Task(generateTaskID(),
                taskDTO.getTaskTitle(),
                new Date(),
                taskDTO.getTaskDeadline(),
                taskDTO.getTaskDescription(),
                taskDTO.getState(),
                userService.getUserById(taskDTO.getTaskManagerID()),
                taskUser,
                taskDTO.getDepartment(),
                taskDTO.getTaskPriority(),
                projectService.findProjectByID(taskDTO.getProjectID())
        );

        //emailService.sendEmaiLTaskCraeted();
        save(newTask);
        taskStatusService.newTaskCreated(newTask);
        taskUser.getUserTasks().add(newTask);
        userRepository.save(taskUser);
    }

    @Override
    public void deleteTask(Long taskID) {
        //TODO: tutaj
        Task task = taskRepository.getTaskById(taskID);
//        for (int i = 0; i < task.getUsers().size(); i++) {
//            task.getUsers().get(i).removeTask(task);
//        }

        remove(task);
    }

    @Override
    public void changeTaskState(Long taskID, EState newState) {
        Task task = taskRepository.getTaskById(taskID);
        taskStatusService.taskStatusChanged(task.getState(), newState, task);
        task.setState(newState);
        //TODO: delete comment
        //emailService.sendEmailTaskStatusChanged();
        taskRepository.save(task);
    }

    @Override
    public void changeTaskUser(Long taskID, Long newUserID, Long previousTaskUserID) {
        Task task = taskRepository.getTaskById(taskID);
        User newTaskUser = userService.getUserById(newUserID);
        User previousTaskUser = userService.getUserById(previousTaskUserID);
        taskStatusService.taskUserChanged(newTaskUser, task, previousTaskUser);
        task.setUser(newTaskUser);
        taskRepository.save(task);
    }

    @Override
    public void changeTaskDeadline(Long taskID, String newDeadline) {
        Task task = taskRepository.getTaskById(taskID);
       LocalDateTime previousDeadline = task.getExpireDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(newDeadline);
            task.setExpireDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        taskStatusService.taskDeadlineChanged(task, previousDeadline, newDeadline);
        taskRepository.save(task);
    }

    @Override
    public List<Task> getAllProjectTasks(Long projectID) {
        return taskRepository.getTasksForProject(projectID);
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

    @Override
    public List<BasicTaskInfoDTO> getBasicTaskInfo(Long userID) {
        String searchQuery =
                "SELECT name as taskName, state as taskState, task_priority as taskPriority," +
                        " CONCAT(first_name, \" \", last_name) AS userFullName, task.id as taskID," +
                        " user.id as userID, pictureurl as userPictureURL, project_name as projectName" +
                        " from" +
                        " task join user on task.user_id = user.id" +
                        " join project on task.project_id = project.id where user.id = :userID";

        Query query = this.entityManager.createNativeQuery(searchQuery).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(BasicTaskInfoDTO.class));
        query.setParameter("userID", userID);
        List<BasicTaskInfoDTO> result = (List<BasicTaskInfoDTO>) query.list();
        return result;
    }

    @Override
    public List<BasicTaskInfoDTO> getBasicTaskInfo() {
        String searchQuery =
                "SELECT name as taskName, state as taskState, task_priority as taskPriority," +
                        " CONCAT(first_name, \" \", last_name) AS userFullName, task.id as taskID," +
                        " user.id as userID, pictureurl as userPictureURL, project_name as projectName" +
                        " from" +
                        " task join user on task.user_id = user.id" +
                        " join project on task.project_id = project.id";

        Query query = this.entityManager.createNativeQuery(searchQuery).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(BasicTaskInfoDTO.class));
        List<BasicTaskInfoDTO> result = (List<BasicTaskInfoDTO>) query.list();
        return result;
    }

    @Override
    public List<BasicTaskInfoDTO> getBasicTaskInfoForProjectWithProjectID(Long projectID) {
        String searchQuery =
                " SELECT name as taskName, state as taskState, task_priority as taskPriority,\n" +
                        "                         CONCAT(first_name,\" \", last_name) AS userFullName, task.id as taskID,\n" +
                        "                         user.id as userID, pictureurl as userPictureURL, project_name as projectName\n" +
                        "                         from\n" +
                        "                        task join user on task.user_id = user.id\n" +
                        "                         join project on task.project_id = project.id where project.id = :projectID";

        Query query = this.entityManager.createNativeQuery(searchQuery).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(BasicTaskInfoDTO.class));
        query.setParameter("projectID", projectID);
        List<BasicTaskInfoDTO> result = (List<BasicTaskInfoDTO>) query.list();
        return result;
    }

    @Override
    public List<BasicTaskInfoDTO> getBasicTaskInfoForUserInProject(Long userID, Long projectID) {
        String searchQuery =
                "SELECT name as taskName, state as taskState, task_priority as taskPriority," +
                        " CONCAT(first_name, \" \", last_name) AS userFullName, task.id as taskID," +
                        " user.id as userID, pictureurl as userPictureURL, project_name as projectName" +
                        " from" +
                        " task join user on task.user_id = user.id" +
                        " join project on task.project_id = project.id where user.id = :userID and project.id = :projectID";

        Query query = this.entityManager.createNativeQuery(searchQuery).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(BasicTaskInfoDTO.class));
        query.setParameter("userID", userID);
        query.setParameter("projectID", projectID);
        List<BasicTaskInfoDTO> result = (List<BasicTaskInfoDTO>) query.list();
        return result;
    }

    @Override
    public List<Long[]> getTaksLength() {
        return taskRepository.getTasksLength();
    }

    @Override
    public List<Long[]> getTaksLength(Long userID) {
        return taskRepository.getTasksLength(userID);
    }

    @Override
    public List<Long[]> getTasksLengthForProjectWithProjectID(Long projectID) {
        return taskRepository.getTasksLengthForProjectWithProjectID(projectID);
    }

    @Override
    public Task getTaskByTaskKey(String taskKEY) {
        return taskRepository.findTaskByTaskID(taskKEY);
    }
}
