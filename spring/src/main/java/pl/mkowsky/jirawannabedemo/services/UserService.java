package pl.mkowsky.jirawannabedemo.services;


import pl.mkowsky.jirawannabedemo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllusers();
    User findUserByUsername(String username);
    User getUserById(int userID);

}
