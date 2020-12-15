package pl.mkowsky.jirawannabedemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mkowsky.jirawannabedemo.dto.ProjectCreationDTO;
import pl.mkowsky.jirawannabedemo.model.Project;
import pl.mkowsky.jirawannabedemo.model.Task;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.repository.ProjectRepository;
import pl.mkowsky.jirawannabedemo.repository.UserRepository;

import java.util.ArrayList;
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
    public void createNewProject(ProjectCreationDTO projectCreationDTO) {
        List<User> projectUsers = new ArrayList<>();
        for(int i = 0; i < projectCreationDTO.getUsersID().length; i++){
            User user = userRepository.findUserById(projectCreationDTO.getUsersID()[i]);
            projectUsers.add(user);
        }
        Project project = new Project(projectCreationDTO.getProjectName(), null, projectUsers, userRepository.findUserById(projectCreationDTO.getProjectManagerID()),
                projectCreationDTO.getProjectIconID());
        projectRepository.save(project);
    }

    @Override
    public List<Project> getProjectNameAndID() {
        return projectRepository.getProjectNameAndID();
    }
}
