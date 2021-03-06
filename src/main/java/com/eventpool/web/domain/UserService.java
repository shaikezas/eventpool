package com.eventpool.web.domain;


import com.eventpool.common.entities.User;




/**
 * Service to handle Users.
 */
public interface UserService {
    User getCurrentUser();
	ResultStatus saveUser(User user);
	ResultStatus validateEmailAndId(String email, Long id);
	public User getUserByUsername(String username);
	public User getUserByUserId(String userId);
	public ResultStatus validateUser(String user);
	public ResultStatus updateUser(User user);
	public ResultStatus resetPassword(User user);
	public User getUserByEmail(String email);
	public ResultStatus addPoints(Long userId,Integer points);
	
}
