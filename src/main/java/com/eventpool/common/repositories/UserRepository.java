package com.eventpool.common.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.entities.User;
import com.eventpool.common.type.Gender;

public interface UserRepository extends JpaRepository<User, Long>{

	public User findByUserName(String userName);
	
	public User findByEmail(String email);
	
	@Modifying
	@Transactional
	@Query(value = "Update User u set u.company=:company, u.phone=:phone, u.mobile=:mobile, u.altEmail=:altEmail, u.homeAddress=:homeAddress,u.officeAddress=:officeAddress" +
			",  u.gender=:gender,u.dob=:dob, u.companyUrl=:companyUrl, u.fname=:fname, u.lname=:lname,u.jobtitle=:jobtitle,u.workPhone=:workPhone where u.id=:id")
	public int updateUser(@Param("id") Long id,
			@Param("company") String company,@Param("phone") String phone,@Param("mobile") String mobile,
			@Param("altEmail") String altEmail,@Param("homeAddress") String homeAddress,@Param("officeAddress") String officeAddress,
			@Param("gender") Gender gender,@Param("companyUrl") String companyUrl,@Param("fname") String fname,@Param("lname") String lname,@Param("dob") Date dob,
			@Param("jobtitle")String jobtitle,@Param("workPhone")String workPhone);
	
	@Modifying
	@Transactional
	@Query(value = "Update User u set u.password=:password where u.id=:id")
	public int resetPassword(@Param("id") Long id,@Param("password") String password);

}
