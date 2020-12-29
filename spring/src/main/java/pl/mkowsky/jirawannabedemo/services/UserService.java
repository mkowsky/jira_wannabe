package pl.mkowsky.jirawannabedemo.services;


import pl.mkowsky.jirawannabedemo.dto.PersonalDataDTO;
import pl.mkowsky.jirawannabedemo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllusers();
    User getUserById(Long userID);
    void save(User user);
    String generateRandomName();
    String generateRandomSurname();
    List<PersonalDataDTO> getOnlyUsersPersonalData();

}
