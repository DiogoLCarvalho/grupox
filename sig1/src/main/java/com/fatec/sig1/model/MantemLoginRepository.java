package com.fatec.sig1.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface MantemLoginRepository extends JpaRepository<Login, Long>{

	UserDetails findByLogin(String login);
}


