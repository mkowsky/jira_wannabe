package pl.mkowsky.jirawannabedemo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mkowsky.jirawannabedemo.dto.PersonalDataDTO;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.services.UserService;

import java.math.BigInteger;
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
    List<PersonalDataDTO> listAllUserNames(){
        //List<User> list = userService.getAllusers();
        return userService.getOnlyUsersPersonalData();

//        for(int i = 0 ;  i < list.size(); i++){
//            PersonalDataDTO temp = new PersonalDataDTO(
//                    list.get(i).getId(),
//                    list.get(i).getFirstName(),
//                    list.get(i).getLastName());
//                    template.add(temp);
//        }
//        return template;
    }

    @GetMapping(value = "/get-user-personal-data/{userID}")
    PersonalDataDTO getUserPersonalData(@PathVariable("userID") Long userID){
        User user = userService.getUserById(userID);
        PersonalDataDTO personalDataDTO = new PersonalDataDTO(
                userID,
                user.getFirstName(),
                user.getLastName(),
                user.getFirstName() + " " + user.getLastName(),
                user.getPictureURL());
        System.out.println(user.getPictureURL());
        return personalDataDTO;
    }

}
