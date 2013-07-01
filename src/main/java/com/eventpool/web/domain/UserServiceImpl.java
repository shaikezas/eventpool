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

       return  ((EventpoolUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
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

	public User getUserByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ResultStatus validateUserName(String userName) {
		if(userName.isEmpty()) {
			return ResultStatus.FAILURE;
		}
		User b = userRepository.findByUserName(userName);
		if(null == b) {
			return ResultStatus.SUCCESS;
		}
		return ResultStatus.USER_EXISTS;
	}
	
	public ResultStatus updateUser(User user){
		userRepository.updateUser(user.getId(), user.getCompany(), user.getPhone(), user.getEmail(), user.getMobile(), user.getAltEmail(),
				user.getHomeAddress(), user.getOfficeAddress(), user.getShippingAddress(), user.getGeneder(), user.getCompanyUrl(), user.getFname(), user.getLname(), user.getDob());
		
		return ResultStatus.SUCCESS;
	}
}
