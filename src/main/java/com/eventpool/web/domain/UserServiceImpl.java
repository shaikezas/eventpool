package com.eventpool.web.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.eventpool.common.entities.User;
import com.eventpool.common.module.EventpoolUserDetails;
import com.eventpool.common.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public User getCurrentUser() {

       User user = null;
       try{
       user = ((EventpoolUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
       }catch(Exception e){
    	 return null;  
       }
       
       return user;
    }

	public ResultStatus saveUser(User user) {
		user = userRepository.save(user);
		return ResultStatus.SUCCESS;
	}

	public ResultStatus validateEmailAndId(String email, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	public User getUserByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ResultStatus validateUser(String user) {
		if(user.isEmpty()) {
			return ResultStatus.FAILURE;
		}
		User b = userRepository.findByEmail(user);
		if(null == b) {
			return ResultStatus.SUCCESS;
		}
		return ResultStatus.USER_EXISTS;
	}
	
	public ResultStatus updateUser(User user){
		userRepository.updateUser(user.getId(), user.getCompany(), user.getPhone(),  user.getMobile(), user.getAltEmail(),
				user.getHomeAddress(), user.getOfficeAddress(), user.getGender(), user.getCompanyUrl(), user.getFname(), user.getLname(), user.getDob(),user.getJobtitle(),user.getWorkPhone());
		
		return ResultStatus.SUCCESS;
	}
	
	public ResultStatus resetPassword(User user){
		userRepository.resetPassword(user.getId(), user.getPassword());
		
		return ResultStatus.SUCCESS;
	}
}
