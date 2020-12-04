package pl.mkowsky.jirawannabedemo.services;

import org.hibernate.query.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mkowsky.jirawannabedemo.dictionary.EChange;
import pl.mkowsky.jirawannabedemo.dictionary.EState;
import pl.mkowsky.jirawannabedemo.dto.TaskStatusDTO;
import pl.mkowsky.jirawannabedemo.model.Task;
import pl.mkowsky.jirawannabedemo.model.TaskStatusChange;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.repository.TaskStatusRepository;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {


    private TaskStatusRepository taskStatusRepository;
    private EntityManager entityManager;

    @Autowired
    public TaskStatusServiceImpl(TaskStatusRepository taskStatusRepository, EntityManager entityManager) {
        this.taskStatusRepository = taskStatusRepository;
        this.entityManager = entityManager;

    }

    @Override
    public void newTaskCreated(Task task) {
        TaskStatusChange taskStatusChange = new TaskStatusChange(EChange.TASK_CREATED,
                "Task created.",
                new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                task);
        taskStatusRepository.save(taskStatusChange);

    }

    @Override
    public void taskStatusChanged(EState previousState, EState newState, Task task) {
        TaskStatusChange taskStatusChange = new TaskStatusChange(EChange.TASK_STATUS_CHANGED,
                "Task status changed from " + previousState.toString() + " to " + newState.toString(),
                new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                task);

        taskStatusRepository.save(taskStatusChange);
    }

    @Override
    public void taskUserChanged(User user, Task task) {
        TaskStatusChange taskStatusChange = new TaskStatusChange(EChange.TASK_USERS,
                "Task has been passed to " + user.getFirstName() + " " + user.getLastName(),
                new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                task);

        taskStatusRepository.save(taskStatusChange);
    }

    @Override
    public void newTaskIssue(String issueDescription, Task task) {
        TaskStatusChange taskStatusChange = new TaskStatusChange(EChange.TASK_USERS,
                "New issue has been reported with description: " + issueDescription,
                new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                task);

        taskStatusRepository.save(taskStatusChange);
    }

    @Override
    public List<TaskStatusChange> getAllTaskChangesForProject(Long projectID) {
        return taskStatusRepository.getAllTaskChangesForProject(projectID);
    }

    @Override
    public List<TaskStatusDTO> testQuery(Long projectID) {
        //1
        // Query query = entityManager.createNativeQuery("select tc.change_description as description from tasks_changes as tc join task on task.id = tc.task_id where task.project_id = 4", TestDTO.class);
        //List<TestDTO> groupList2 = (List<TestDTO>) query.getResultList();

//2
//        List<Object[]> template = taskStatusRepository.testQuery(projectID);
//        List<TaskStatusDTO> templateDwa = new ArrayList<>();
//
//        for (int i = 0; i < template.size(); i++) {
//            TaskStatusDTO taskStatusDTO = new TaskStatusDTO(
//                    (BigInteger) template.get(i)[0],
//                    (String) template.get(i)[1],
//                    (Timestamp) template.get(i)[2],
//                    (String) template.get(i)[3],
//                    (BigInteger) template.get(i)[4]
//            );
//            templateDwa.add(taskStatusDTO);
//        }
//
//        return templateDwa;

//2
        String searchQuery = "select tc.id, tc.change_description as changeDescription, tc.change_date as changeDate, tc.change_type as changeType, tc.task_id as taskID, task.name as taskName, task.taskid as taskKEY from tasks_changes as tc join task on task.id = tc.task_id where task.project_id = :projectID order by change_date desc LIMIT 6";

        Query query = this.entityManager.createNativeQuery(searchQuery).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer( TaskStatusDTO.class));

        query.setParameter("projectID", projectID);

        List<TaskStatusDTO> result = (List<TaskStatusDTO>) query.list();
        return result;
    }
}


