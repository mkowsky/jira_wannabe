package pl.mkowsky.jirawannabedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.services.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class TemplateController {

    private UserService userService;

    @Autowired
    public TemplateController(UserService userService){
        this.userService = userService;
    }


    @RequestMapping(value = "/test")
    List<User> listAllUsers(){
        System.out.println("Inside listAllUsers");
//        List<User> tempList = userService.getAllusers();
//        for(int i = 0; i < tempList.size(); i++){
//            System.out.println(tempList.get(i).)
//        }
        return userService.getAllusers();
    };


}
