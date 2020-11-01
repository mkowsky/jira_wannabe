package pl.mkowsky.jirawannabedemo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mkowsky.jirawannabedemo.PersonalData;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.services.UserService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value = "/get-names")
    List<PersonalData> listAllUserNames(){
        List<User> list = userService.getAllusers();
        List<PersonalData> template =  new ArrayList<>();
        for(int i = 0 ;  i < list.size(); i++){
            PersonalData temp = new PersonalData(
                    list.get(i).getId(),
                    list.get(i).getFirstName(),
                    list.get(i).getLastName());
                    template.add(temp);
        }
        return template;
    }

}
