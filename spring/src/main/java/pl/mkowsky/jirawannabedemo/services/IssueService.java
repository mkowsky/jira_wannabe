package pl.mkowsky.jirawannabedemo.services;


import pl.mkowsky.jirawannabedemo.model.Issue;

public interface IssueService {
    void newIssueReported(Long taskID, String issueDescription);
}
