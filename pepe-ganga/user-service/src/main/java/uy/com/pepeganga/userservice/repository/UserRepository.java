package uy.com.pepeganga.userservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import uy.com.pepeganga.userservice.entities.User;


public interface UserRepository  extends JpaRepository<User, Integer>{


	Page<User> findAllByEmail(String email, Pageable pageable);
	
}
