package pl.mkowsky.jirawannabedemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.mkowsky.jirawannabedemo.dictionary.EIssue;
import pl.mkowsky.jirawannabedemo.model.Issue;

import pl.mkowsky.jirawannabedemo.repository.IssueRepository;
import pl.mkowsky.jirawannabedemo.repository.TaskRepository;


import java.time.ZoneId;
import java.util.Date;

@Service

public class IssueServiceImpl implements IssueService {

    private IssueRepository issueRepository;
    private TaskRepository taskRepository;
    private TaskStatusService taskStatusService;


    @Autowired
    public IssueServiceImpl(TaskRepository taskRepository, IssueRepository issueRepository, TaskStatusService taskStatusService) {
        this.taskRepository = taskRepository;
        this.issueRepository = issueRepository;
        this.taskStatusService = taskStatusService;
    }

    @Override
    public void newIssueReported(Long taskID, String issueDescription, EIssue issueType) {
        Issue issue = new Issue(issueDescription,
                issueType,
                taskRepository.getTaskById(taskID),
                new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        issueRepository.save(issue);
        taskStatusService.newTaskIssue(issue, taskRepository.getTaskById(taskID));
    }
}
