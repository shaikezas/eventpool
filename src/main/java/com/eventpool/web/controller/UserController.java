package com.eventpool.web.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.eventpool.common.module.HtmlEmailService;
import com.eventpool.common.module.PasswordGenerator;
import com.eventpool.web.domain.ResponseMessage;
import com.eventpool.web.domain.ResultStatus;
import com.eventpool.web.domain.UserService;
import com.eventpool.web.forms.SignupForm;
import com.eventpool.web.forms.UserForm;

@Controller
public class UserController {
    @Resource
    private UserService userService;
    
    @Autowired
    private EventpoolMapper  mapper;
    
    @Resource
    private HtmlEmailService htmlEmailService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }
    
    /*@RequestMapping(value = "/createuser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage createUser(@RequestParam("userName")String userName,@RequestParam("password") String password){
    	System.out.println("create User");
    	ResultStatus validate = userService.validateUser(userName);
    	if(validate.equals(ResultStatus.USER_EXISTS)){
    		return new ResponseMessage(ResponseMessage.Type.error, "User already registered ");
    	}
    	User user = new User(userName,password);
        userService.saveUser(user);
        return new ResponseMessage(ResponseMessage.Type.success, "Successfully created user");
    }*/
    
    @RequestMapping(value = "/signupuser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage signupUser(@RequestBody SignupForm signupForm){
    	System.out.println("create User");
    	
    	ResultStatus validate = userService.validateUser(signupForm.getEmail());
    	if(validate.equals(ResultStatus.USER_EXISTS)){
    		return new ResponseMessage(ResponseMessage.Type.error, "User already registered ");
    	}
    	if(validate.equals(ResultStatus.FAILURE)){
    		return new ResponseMessage(ResponseMessage.Type.error, "Enter user details ");
    	}
    	User user = new User(signupForm.getEmail(),signupForm.getPassword(),signupForm.getFname(),signupForm.getLname());
        userService.saveUser(user);
        return new ResponseMessage(ResponseMessage.Type.success, "Successfully created user");
    }
    
    
    @RequestMapping(value = "/account/updateuser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateUser(@RequestBody UserForm userForm){
    	System.out.println("create User");
    	User user = userService.getCurrentUser();
    	mapper.mapUserForm(userForm, user);
    	ResultStatus status = userService.updateUser(user);
    	if(status.equals(ResultStatus.SUCCESS)){
    		List<String> toList = new ArrayList<String>();
    		String subject = "Successfully updated user.";
 			toList.add(user.getEmail());
    		htmlEmailService.sendMail(toList, subject, subject, null,null);
    		return new ResponseMessage(ResponseMessage.Type.success, "Successfully updated user");
    	}
        return new ResponseMessage(ResponseMessage.Type.error, "Failed to update user");
    }
    
    @RequestMapping(value = "/account/resetpassword", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage resetPassword(@RequestParam ("password") String password){
    	System.out.println("Password resetting...");
    	User user = userService.getCurrentUser();
//    	mapper.mapUserForm(userForm, user);
    	user.updatePassword(password);
    	ResultStatus status = userService.resetPassword(user);
    	if(status.equals(ResultStatus.SUCCESS)){
    		List<String> toList = new ArrayList<String>();
    		String subject = "Password updated successfully.";
 			toList.add(user.getEmail());
    		htmlEmailService.sendMail(toList, subject, subject, null,null);
    		return new ResponseMessage(ResponseMessage.Type.success, subject);
    	}
        return new ResponseMessage(ResponseMessage.Type.error, "Failed to reset password.");
    }
    
    
    @RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage forgotPassword(@RequestParam("email")  String email){
    	System.out.println("Password forgot...");
    	User user =userService.getUserByEmail(email);
    	
    	if(user==null){
    		return new ResponseMessage(ResponseMessage.Type.error, "User does not exist.");
    	}
    	String tmpPassword = PasswordGenerator.generatePassword();
    	user.updatePassword(tmpPassword);
    	ResultStatus status = userService.resetPassword(user);
    	if(status.equals(ResultStatus.SUCCESS)){
    		String subject = "Password reset successfully.";
    		String body = "New password : "+tmpPassword;
    		List<String> toList = new ArrayList<String>();
 			toList.add(email);
    		htmlEmailService.sendMail(toList, subject, body, null,null);
    		return new ResponseMessage(ResponseMessage.Type.success, subject);
    	}
        return new ResponseMessage(ResponseMessage.Type.error, "Failed to reset password.");
    }
    
    @RequestMapping(value = "/account/getuser", method = RequestMethod.GET)
    @ResponseBody
    public UserForm getUser(){
    	System.out.println("get User");
    	User user = userService.getCurrentUser();
    	UserForm userForm = new UserForm();
    	mapper.mapUser(user, userForm);
        return userForm;
    }

}