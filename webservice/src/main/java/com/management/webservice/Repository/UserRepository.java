package com.management.webservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.webservice.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);

}
