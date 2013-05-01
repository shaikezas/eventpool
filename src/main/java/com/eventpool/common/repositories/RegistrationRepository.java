package com.eventpool.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventpool.common.entities.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long>{

}
