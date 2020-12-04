package pl.mkowsky.jirawannabedemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mkowsky.jirawannabedemo.model.Project;
import pl.mkowsky.jirawannabedemo.model.Task;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.repository.ProjectRepository;
import pl.mkowsky.jirawannabedemo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Project> getAllProjcets() {
        return projectRepository.findAll();
    }

    @Override
    public Project findProjectByID(Long projectID) {
        return projectRepository.findProjectById(projectID);
    }

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Override
    public List<Long> getAllProjectUsers(Long projectID) {
        return projectRepository.getAllProjectUsers(projectID);
    }

    @Override
    public void createNewProject(Long projectManagerID, String projectName) {
        Project project = new Project(projectName, null, null, userRepository.findUserById(projectManagerID));
        projectRepository.save(project);
    }
}
