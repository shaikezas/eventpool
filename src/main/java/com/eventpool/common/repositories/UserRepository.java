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
	
	@Modifying
	@Transactional
	@Query(value = "Update User u set u.company=:company, u.phone=:phone, u.email=:email, u.mobile=:mobile, u.altEmail=:altEmail, u.homeAddress=:homeAddress,u.officeAddress=:officeAddress" +
			" u.shippingAddress=:shippingAddress, u.geneder=:geneder,u.dob =:dob, u.companyUrl=:companyUrl, u.fname=:fname, u.lname=:lname where o.id=:id")
	public int updateUser(@Param("id") Long id,
			@Param("company") String company,@Param("phone") String phone,@Param("email") String email,@Param("mobile") String mobile,
			@Param("altEmail") String altEmail,@Param("homeAddress") String homeAddress,@Param("officeAddress") String officeAddress,@Param("shippingAddress") String shippingAddress,
			@Param("geneder") Gender geneder,@Param("companyUrl") String companyUrl,@Param("fname") String fname,@Param("lname") String lname,@Param("dob") Date dob);

}
