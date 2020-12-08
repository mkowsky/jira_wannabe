package pl.mkowsky.jirawannabedemo.dto;

public class ProjectCreationDTO {

    private String projectName;
    private Long[] usersID;
    private Long projectManagerID;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long[] getUsersID() {
        return usersID;
    }

    public void setUsersID(Long[] usersID) {
        this.usersID = usersID;
    }

    public Long getProjectManagerID() {
        return projectManagerID;
    }

    public void setProjectManagerID(Long projectManagerID) {
        this.projectManagerID = projectManagerID;
    }
}
