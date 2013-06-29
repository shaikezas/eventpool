package com.eventpool.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventpool.common.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public User findByUserName(String userName);


}
