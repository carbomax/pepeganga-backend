package uy.com.pepeganga.userservice.service;

import java.util.List;

import org.springframework.data.domain.Page;

import uy.com.pepeganga.userservice.entities.User;
import uy.com.pepeganga.userservice.models.UserFaker;

public interface IUserService {

	List<UserFaker> getUsersFakers();
	
	List<User> getAllUsers();
	
	User saveUser(User user);
	
	Page<User> getUsersPage(User user, int page, int size );
}
