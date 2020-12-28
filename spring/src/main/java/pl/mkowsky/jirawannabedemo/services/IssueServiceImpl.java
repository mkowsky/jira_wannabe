package pl.mkowsky.jirawannabedemo.services;

import org.hibernate.query.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.mkowsky.jirawannabedemo.dictionary.EIssue;
import pl.mkowsky.jirawannabedemo.dictionary.EPriority;
import pl.mkowsky.jirawannabedemo.dictionary.EStatus;
import pl.mkowsky.jirawannabedemo.dto.IssueDTO;
import pl.mkowsky.jirawannabedemo.model.Issue;

import pl.mkowsky.jirawannabedemo.repository.IssueRepository;
import pl.mkowsky.jirawannabedemo.repository.TaskRepository;


import javax.persistence.EntityManager;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service

public class IssueServiceImpl implements IssueService {

    private IssueRepository issueRepository;
    private TaskRepository taskRepository;
    private TaskStatusService taskStatusService;
    private EntityManager entityManager;
    private UserService userService;


    @Autowired
    public IssueServiceImpl(TaskRepository taskRepository, IssueRepository issueRepository, TaskStatusService taskStatusService, EntityManager entityManager,
                            UserService userService) {
        this.taskRepository = taskRepository;
        this.issueRepository = issueRepository;
        this.taskStatusService = taskStatusService;
        this.entityManager = entityManager;
        this.userService = userService;
    }

    @Override
    public void newIssueReported(Long taskID, String issueDescription, EIssue issueType, EPriority issuePriority, Long reportingUserID) {

        Issue issue = new Issue(generateIssueKey(),
                EStatus.OPEN,
                issuePriority,
                issueDescription,
                issueType,
                taskRepository.getTaskById(taskID),
                new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                userService.getUserById(reportingUserID));
        issueRepository.save(issue);
        taskStatusService.newTaskIssue(issue, taskRepository.getTaskById(taskID));
    }

    @Override
    public List<IssueDTO> getAllIssuesForProjectWithProjectID(Long projectID) {
        String searchQuery = "SELECT issue.id as issueID, issue.description as issueDescription, issue.issue_date as issueDate, issue.issue_type as issueType,\n" +
                " task.taskid as taskKEY,  issue.issueKEY as issueKEY, issue.reported_by_id as reportedByID,\n" +
                " issue.issue_priority as issuePriority, issue.issue_status as issueStatus,  CONCAT(user.first_name,\" \", user.last_name) AS userFullName,\n" +
                " user.pictureURL as pictureURL\n" +
                " from issue join task on issue.task_id = task.id join project on task.project_id = project.id join user on user.id = issue.reported_by_id where project.id = :projectID";
        Query query = this.entityManager.createNativeQuery(searchQuery).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(IssueDTO.class));
        query.setParameter("projectID", projectID);
        List<IssueDTO> result = (List<IssueDTO>) query.list();
        return result;
    }

    public String generateIssueKey(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder stringBuilder = new StringBuilder(10);
        stringBuilder.append("ISS-");
        for (int i = 0; i < 6; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            stringBuilder.append(AlphaNumericString.charAt(index));
        }
        return stringBuilder.toString();

    }
}
