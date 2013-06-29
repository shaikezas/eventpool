package com.eventpool.common.module;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.eventpool.common.entities.User;

public interface EventpoolUserDetailsService extends UserDetailsService{
	public User getUserFromSession();
}
