package pl.mkowsky.jirawannabedemo.services;


import pl.mkowsky.jirawannabedemo.dictionary.EIssue;
import pl.mkowsky.jirawannabedemo.dictionary.EPriority;
import pl.mkowsky.jirawannabedemo.dto.IssueDTO;
import pl.mkowsky.jirawannabedemo.model.Issue;

import java.util.List;

public interface IssueService {
    void newIssueReported(Long taskID, String issueDescription, EIssue issueType, EPriority issuePriority, Long reportingUserID);
    List<IssueDTO>getAllIssuesForProjectWithProjectID(Long projectID);
}
