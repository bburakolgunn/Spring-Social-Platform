package com.management.webservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.webservice.Entity.Token;

public interface TokenRepository extends JpaRepository<Token, String> {

}
