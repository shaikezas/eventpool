package com.eventpool.web.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventpool.common.entities.User;
import com.eventpool.common.module.EventpoolMapper;
import com.eventpool.web.domain.ResponseMessage;
import com.eventpool.web.domain.ResultStatus;
import com.eventpool.web.domain.UserService;
import com.eventpool.web.forms.UserForm;

@Controller
public class UserController {
    @Resource
    private UserService userService;
    
    @Autowired
    private EventpoolMapper  mapper;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public User getCurrentUser() {
    	System.out.println("getCurrentUser");
        return userService.getCurrentUser();
    }
    
    @RequestMapping(value = "/createuser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage createUser(@RequestParam("userName")String userName,@RequestParam("password") String password){
    	System.out.println("create User");
    	ResultStatus validate = userService.validateUserName(userName);
    	if(validate.equals(ResultStatus.USER_EXISTS)){
    		return new ResponseMessage(ResponseMessage.Type.error, "User already registered ");
    	}
        userService.saveUser(new User(userName,password));
        return new ResponseMessage(ResponseMessage.Type.success, "Successfully created user");
    }
    
    @RequestMapping(value = "/account/updateuser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateUser(@RequestBody UserForm userForm){
    	System.out.println("create User");
    	User user = new User();
    	mapper.mapUserForm(userForm, user);
    	ResultStatus status = userService.updateUser(user);
    	if(status.equals(ResultStatus.SUCCESS)){
    		return new ResponseMessage(ResponseMessage.Type.success, "Successfully updated user");
    	}
        return new ResponseMessage(ResponseMessage.Type.error, "Failed to update user");
    }
    
    @RequestMapping(value = "/account/resetpassword/{newPass}/{confirmPass}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage resetPassword(@RequestBody UserForm userForm,@PathVariable("newPass") String newPass,@PathVariable("confirmPass") String confirmPass){
    	System.out.println("Password resetting...");
    	User user = new User();
    	user.updatePassword(userForm.getPassword(),newPass,confirmPass);
    	mapper.mapUserForm(userForm, user);
    	ResultStatus status = userService.resetPassword(user);
    	if(status.equals(ResultStatus.SUCCESS)){
    		return new ResponseMessage(ResponseMessage.Type.success, "Password reset successfully.");
    	}
        return new ResponseMessage(ResponseMessage.Type.error, "Failed to reset password.");
    }
    
    @RequestMapping(value = "/account/getuser", method = RequestMethod.GET)
    @ResponseBody
    public UserForm getUser(){
    	System.out.println("create User");
    	User user = userService.getCurrentUser();
    	UserForm userForm = new UserForm();
    	mapper.mapUser(user, userForm);
        return userForm;
    }

}