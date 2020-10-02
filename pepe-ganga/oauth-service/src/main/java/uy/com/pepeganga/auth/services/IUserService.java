package uy.com.pepeganga.auth.services;

public interface IUserService {

	uy.com.pepeganga.business.common.entities.User findByEmail(String userName);
	
}
