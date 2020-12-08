package pl.mkowsky.jirawannabedemo.services;


import pl.mkowsky.jirawannabedemo.dto.PersonalDataDTO;
import pl.mkowsky.jirawannabedemo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllusers();
    Optional<User> findUserByUsername(String username);
    User getUserById(Long userID);
    User findByUsername(String username);
    void save(User user);
    String generateRandomName();
    String generateRandomSurname();
    List<PersonalDataDTO> getOnlyUsersPersonalData();

}
