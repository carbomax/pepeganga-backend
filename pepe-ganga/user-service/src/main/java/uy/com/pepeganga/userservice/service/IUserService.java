package uy.com.pepeganga.userservice.service;

import java.util.List;

import org.springframework.data.domain.Page;

import uy.com.pepeganga.userservice.entities.Profile;
import uy.com.pepeganga.userservice.entities.User;
import uy.com.pepeganga.userservice.models.UserFaker;

public interface IUserService {

	List<UserFaker> getUsersFakers();
	
	List<User> getAllUsers();
	
	Profile saveUserProfile(Profile profile);

	Profile updateUserProfile(Profile profile,  Integer profileId, Integer userId);
	
	Page<User> getUsersPage(User user, int page, int size );

	void deleteUser(Integer id);
}
