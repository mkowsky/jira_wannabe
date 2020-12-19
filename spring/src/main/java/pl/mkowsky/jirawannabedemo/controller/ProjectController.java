package pl.mkowsky.jirawannabedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mkowsky.jirawannabedemo.dto.BasicProjectDTO;
import pl.mkowsky.jirawannabedemo.dto.PersonalDataDTO;
import pl.mkowsky.jirawannabedemo.dto.ProjectCreationDTO;
import pl.mkowsky.jirawannabedemo.model.Project;
import pl.mkowsky.jirawannabedemo.model.Task;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.services.ProjectService;
import pl.mkowsky.jirawannabedemo.services.UserService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/projects")
public class ProjectController {

    private ProjectService projectService;
    private UserService userService;


    @Autowired
    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @PostMapping(value = "/create-new-project")
    void createNewProject(@RequestBody ProjectCreationDTO projectCreationDTO) {
        projectService.createNewProject(projectCreationDTO);

    }

    @GetMapping(value = "get-project-details/{projectID}")
    Project getProjectDetails(@PathVariable("projectID") Long projectID) {
        return projectService.findProjectByID(projectID);
    }

    @GetMapping(value = "/list-all")
    List<Project> getAllProjects() {
        return projectService.getAllProjcets();
    }

    @GetMapping(value = "/list-all-project-users/{projectID}")
    List<User> getAllProjectusers(@PathVariable("projectID") Long projectID) {
        List<Long> template = projectService.getAllProjectUsers(projectID);
        List<User> projectUsers = new ArrayList<>();
        for (int i = 0; i < template.size(); i++) {
            projectUsers.add(userService.getUserById(template.get(i)));
        }
        return projectUsers;
    }

    @GetMapping(value = "/get-all-team-members-personal-data/{projectID}")
    List<PersonalDataDTO> getAllProjectMembersPersonalData(@PathVariable("projectID") Long projectID) {
        List<Long> template = projectService.getAllProjectUsers(projectID);
        List<PersonalDataDTO> projectUsers = new ArrayList<>();
        for (int i = 0; i < template.size(); i++) {
            User user = userService.getUserById(template.get(i));
            projectUsers.add(new PersonalDataDTO(user.getId(), user.getFirstName() + " " + user.getLastName()));
        }
        return projectUsers;
    }



    //TODO: uniemozliwic dodanie uzytkownika jesli jzu jest w projekcie
    @PostMapping(value = "/add-user-to-project/{projectID}/{userID}")
    void addUserToProject(@PathVariable("projectID") Long projectID, @PathVariable("userID") Long userID) {
        Project project = projectService.findProjectByID(projectID);
        project.addUser(userService.getUserById(userID));
        projectService.save(project);

    }

    @GetMapping(value = "/get-all-user-projects/{userID}")
    List<Project> getAllUserProjects(@PathVariable("userID") Long userID) {
        return userService.getUserById(userID).getProjects();
    }

    @GetMapping(value = "/get-project-name-and-id")
    List<BasicProjectDTO> getProjectNameAndID(@RequestParam("userID") Long userID) {
        User user = userService.getUserById(userID);
        if (user.checkIfProjectManager()) {
            return projectService.getProjectNameAndID();
        } else {
            List<Project> userProjects = userService.getUserById(userID).getProjects();
            List<BasicProjectDTO> userProjectsBasicInfo = new ArrayList<>();
            for (int i = 0; i < userProjects.size(); i++) {
                BasicProjectDTO basicProjectDTO = new BasicProjectDTO(userProjects.get(i).getId(),
                        userProjects.get(i).getProjectName());
                userProjectsBasicInfo.add(basicProjectDTO);
            }
            return userProjectsBasicInfo;
        }


    }


}
