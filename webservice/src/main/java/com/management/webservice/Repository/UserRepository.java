package com.management.webservice.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.webservice.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);

	User findByactivationtoken(String token);
	
	Page<User> findByIdNot(long id,Pageable page);
	
	User findByPasswordResetToken(String passwordResetToken);

}
