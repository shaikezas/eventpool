package com.eventpool.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventpool.common.dto.Region;
import com.eventpool.common.entities.User;
import com.eventpool.common.module.EntityUtilities;
import com.eventpool.common.module.EventpoolMapper;
import com.eventpool.common.module.PasswordGenerator;
import com.eventpool.web.domain.ResponseMessage;
import com.eventpool.web.domain.ResultStatus;
import com.eventpool.web.domain.UserService;
import com.eventpool.web.forms.SignupForm;
import com.eventpool.web.forms.UserForm;
import com.zeoevent.zeomail.dto.MailDTO;
import com.zeoevent.zeomail.service.HtmlEmailService;
import com.zeoevent.zeomail.service.MailService;

@Controller
public class UserController {
    @Resource
    private UserService userService;
    
    @Autowired
    private EventpoolMapper  mapper;
    
    @Resource
    private EntityUtilities  entityUtilities;
    
    @Resource
    private MailService mailService;

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
    	user.setUserName(user.getFname()+" "+user.getLname());
    	ResultStatus status = userService.updateUser(user);
    	if(status.equals(ResultStatus.SUCCESS)){
    		List<String> toList = new ArrayList<String>();
    		String subject = "Successfully updated user.";
 			toList.add(user.getEmail());
 			MailDTO mailDTO = new MailDTO();
 			mailDTO.setSubject(subject);
 			mailDTO.setToList(toList);
 			mailDTO.setBody(subject);
    		mailService.push(mailDTO);
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
			MailDTO mailDTO = new MailDTO();
 			mailDTO.setSubject(subject);
 			mailDTO.setToList(toList);
 			mailDTO.setBody(subject);
    		mailService.push(mailDTO);
    		return new ResponseMessage(ResponseMessage.Type.success, subject);
    	}
        return new ResponseMessage(ResponseMessage.Type.error, "Failed to reset password.");
    }
    
    
    @RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage forgotPassword(@RequestParam("email")  String email){
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
			MailDTO mailDTO = new MailDTO();
 			mailDTO.setSubject(subject);
 			mailDTO.setToList(toList);
 			mailDTO.setBody(subject);
    		mailService.push(mailDTO);

    		return new ResponseMessage(ResponseMessage.Type.success, subject);
    	}
        return new ResponseMessage(ResponseMessage.Type.error, "Failed to reset password.");
    }
    
    @RequestMapping(value = "/account/getuser", method = RequestMethod.GET)
    @ResponseBody
    public UserForm getUser(){
    	User user = userService.getCurrentUser();
    	UserForm userForm = new UserForm();
    	mapper.mapUser(user, userForm);
       	if(userForm.getHomeAddress().getCityId()!=null){
        	Map<Integer, Region> csc = entityUtilities.getCitiesWithStateAndCountry();
        	Region region = csc.get(userForm.getHomeAddress().getCityId());
        	userForm.getHomeAddress().setCityName(region.getCityName());
        	userForm.getHomeAddress().setStateName(region.getStateName());
        	userForm.getHomeAddress().setCountryName(region.getCountryName());
        	}
      	if(userForm.getOfficeAddress().getCityId()!=null){
        	Map<Integer, Region> csc = entityUtilities.getCitiesWithStateAndCountry();
        	Region region = csc.get(userForm.getOfficeAddress().getCityId());
        	userForm.getOfficeAddress().setCityName(region.getCityName());
        	userForm.getOfficeAddress().setStateName(region.getStateName());
        	userForm.getOfficeAddress().setCountryName(region.getCountryName());
        	}
        return userForm;
    }
    
}