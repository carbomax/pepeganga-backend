package uy.com.pepeganga.userservice.service;

import org.springframework.data.domain.Page;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.com.pepeganga.business.common.entities.User;
import uy.com.pepeganga.userservice.models.ResetPassword;

import java.util.List;
import java.util.Map;

public interface IUserService {

	User findByEmail(String email);

	List<User> getAllUsers();
	
	Profile saveUserProfile(Profile profile);

	Profile updateUserProfile(Profile profile,  Integer profileId, Integer userId);

	void deleteUser(Integer id);

	User enableOrDisable(Integer id,  boolean enableOrDisable);

	User updateUser(User user , Integer id);


    boolean isValidTokenToResetPassword(String token);

    boolean isUserEnabledByToken(String token);

    Map<String, Object> changePassword(ResetPassword resetPassword);
}
