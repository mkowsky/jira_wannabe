package pl.mkowsky.jirawannabedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    //TODO: uniemozliwic dodanie uzytkownika jesli jzu jest w projekcie
    @PostMapping(value = "/add-user-to-project/{projectID}/{userID}")
    void addUserToProject(@PathVariable("projectID") Long projectID, @PathVariable("userID") Long userID) {
        Project project = projectService.findProjectByID(projectID);
        project.addUser(userService.getUserById(userID));
        projectService.save(project);

    }

}
