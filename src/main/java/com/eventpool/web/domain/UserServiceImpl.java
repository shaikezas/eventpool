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
}
