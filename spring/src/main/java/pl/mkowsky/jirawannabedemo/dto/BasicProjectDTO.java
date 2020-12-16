package pl.mkowsky.jirawannabedemo.dto;

import java.math.BigInteger;

public class BasicProjectDTO {



    private Long id;
    private String projectName;

    public BasicProjectDTO(){

    }

    public BasicProjectDTO(Long id, String projectName) {
        this.id = id;
        this.projectName = projectName;
    }

    public Long getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id.longValue();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
