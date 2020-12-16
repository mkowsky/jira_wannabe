package pl.mkowsky.jirawannabedemo.dto;

import java.math.BigInteger;

public class PersonalDataDTO {


    Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String pictureURL;

    public PersonalDataDTO(){

    }

    public PersonalDataDTO(Long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public PersonalDataDTO(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PersonalDataDTO(Long id, String firstName, String lastName, String fullName, String pictureURL) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.pictureURL = pictureURL;
    }


    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id.longValue();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
