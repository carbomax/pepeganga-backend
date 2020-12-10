package uy.com.pepeganga.auth.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import uy.com.pepeganga.business.common.entities.User;
import uy.com.pepeganga.business.common.models.AuthAddInformationClaim;

import java.util.Map;

public interface IUserService {

	User findByEmail(String userName);

	AuthAddInformationClaim findProfileByUserEmail(String email);

	User updateUser(User user,  Integer id);

	Map<String, Object> sendEmailToResetPassword(String email, String url);

    boolean isValidTokenToResetPassword(String token);
}
