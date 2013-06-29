package com.eventpool.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventpool.common.entities.User;
import com.eventpool.web.domain.UserService;

@Controller
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public User getCurrentUser() {
    	System.out.println("getCurrentUser");
        return userService.getCurrentUser();
    }
    
 /*   @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public void createUser(String userName,String password){
    	System.out.println("create User");
        userService.saveUser(new User(userName,password));
    }*/

}