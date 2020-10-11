package uy.com.pepeganga.auth.services;

import uy.com.pepeganga.business.common.entities.User;
import uy.com.pepeganga.business.common.models.AuthAddInformationClaim;

public interface IUserService {

	User findByEmail(String userName);

	AuthAddInformationClaim findProfileByUserEmail(String email);

	User updateUser(User user,  Integer id);
	
}
