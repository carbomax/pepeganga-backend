package uy.com.pepeganga.userservice.service;

import org.springframework.data.domain.Page;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.com.pepeganga.business.common.entities.User;

import java.util.List;

public interface IUserService {

	User findByEmail(String email);

	List<User> getAllUsers();
	
	Profile saveUserProfile(Profile profile);

	Profile updateUserProfile(Profile profile,  Integer profileId, Integer userId);
	
	Page<User> getUsersPage(User user, int page, int size );

	void deleteUser(Integer id);
}
