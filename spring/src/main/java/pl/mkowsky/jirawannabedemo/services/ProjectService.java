package pl.mkowsky.jirawannabedemo.services;

import pl.mkowsky.jirawannabedemo.dto.BasicProjectDTO;
import pl.mkowsky.jirawannabedemo.dto.ProjectCreationDTO;
import pl.mkowsky.jirawannabedemo.model.Project;
import pl.mkowsky.jirawannabedemo.model.User;


import java.util.List;


public interface ProjectService {

    Project findProjectByID(Long projectID);
    List<Project> getAllProjcets();

    void save(Project proejct);

    List<Long> getAllProjectUsers(Long projectID);
    void createNewProject(ProjectCreationDTO projectCreationDTO);

    List<BasicProjectDTO> getProjectNameAndID();
}
