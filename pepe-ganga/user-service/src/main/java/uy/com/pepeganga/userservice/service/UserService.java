package uy.com.pepeganga.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.com.pepeganga.business.common.entities.User;
import uy.com.pepeganga.userservice.entities.VerificationToken;
import uy.com.pepeganga.userservice.models.ResetPassword;
import uy.com.pepeganga.userservice.repository.ProfileRepository;
import uy.com.pepeganga.userservice.repository.UserRepository;
import uy.com.pepeganga.userservice.repository.VerificationTokenRepository;

import java.util.*;


@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final ProfileRepository profileRepository;

    private final BCryptPasswordEncoder cryptPasswordEncoder;

    @Autowired
    VerificationTokenRepository tokenRepository;


    public UserService(UserRepository userRepository, ProfileRepository profileRepository, BCryptPasswordEncoder cryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.cryptPasswordEncoder = cryptPasswordEncoder;
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Profile saveUserProfile(Profile profile) {
        if (userRepository.existsByEmail(profile.getUser().getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("User with email: %s exist", profile.getUser().getEmail()));
        }
        User userToSaved = profile.getUser();
        userToSaved.setPassword(cryptPasswordEncoder.encode(userToSaved.getPassword()));
        User userSaved = userRepository.save(userToSaved);
        profile.setUser(userSaved);
        return profileRepository.save(profile);
    }

    @Override
    @Transactional
    public Profile updateUserProfile(Profile profile, Integer profileId, Integer userId) {


        Optional<Profile> profileToUpdatedDb = profileRepository.findById(profileId);
        Optional<User> userToUpdatedDb = userRepository.findById(userId);
        if (profileToUpdatedDb.isPresent() && userToUpdatedDb.isPresent()) {

            if(!userToUpdatedDb.get().getEmail().equals(profile.getUser().getEmail()) && userRepository.existsByEmail(profile.getUser().getEmail())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("User with email: %s exist", profile.getUser().getEmail()));
            }

                User userToUpdate = userToUpdatedDb.get();
                userToUpdate.setEmail(profile.getUser().getEmail());
                if(!StringUtils.isEmpty(profile.getUser().getPassword())) {
                	 userToUpdate.setPassword(cryptPasswordEncoder.encode(profile.getUser().getPassword()));
                }               
                userToUpdate.setRoles(profile.getUser().getRoles());
                userToUpdate.setEnabled(profile.getUser().getEnabled());
                User userUpdated = userRepository.save(userToUpdate);
                profileToUpdatedDb.get().setFirstName(profile.getFirstName());
                profileToUpdatedDb.get().setLastName(profile.getLastName());
                profileToUpdatedDb.get().setBusinessName(profile.getBusinessName());
                profileToUpdatedDb.get().setImage(profile.getImage());
                profileToUpdatedDb.get().setRut(profile.getRut());
                profileToUpdatedDb.get().setStore(profile.getStore());
                profileToUpdatedDb.get().setAddress(profile.getAddress());
                profileToUpdatedDb.get().setMargins(profile.getMargins());
                profileToUpdatedDb.get().setMarketplaces(profile.getMarketplaces());
                profileToUpdatedDb.get().setUser(userUpdated);
                return profileRepository.save(profileToUpdatedDb.get());

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not updated with id %s", profile.getUser().getId()));
        }

    }


    @Override
    @Transactional
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not deleted with id %s, it not exist", id));
        }
        profileRepository.deleteProfileByUserId(id);
        userRepository.deleteById(id);
    }

    @Override
    public User enableOrDisable(Integer id, boolean enableOrDisable) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            User userToUpdate = user.get();
            userToUpdate.setEnabled(enableOrDisable);
            userToUpdate.setLoginAttempts(0);
            return userRepository.save(userToUpdate);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not enabled or disabled with id %s, it not exist", id));
        }
    }

    @Override
    public User updateUser(User user, Integer id) {
        Optional<User> userFounded = userRepository.findById(id);
        if (userFounded.isPresent()) {
            User userToUpdate = userFounded.get();
            userToUpdate.setEnabled(user.getEnabled());
            userToUpdate.setCreateAt(user.getCreateAt());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setLoginAttempts(user.getLoginAttempts());
           userToUpdate.setPassword(user.getPassword());
            userToUpdate.setRoles(user.getRoles());
            return userRepository.save(userToUpdate);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not updated with id %s, it not exist", id));
        }
    }

    @Override
    public boolean isValidTokenToResetPassword(String token) {
        VerificationToken verificationTokenFounded = tokenRepository.findByToken(token);
        if(Objects.isNull(verificationTokenFounded)) return false;
        return !isTokenExpired(verificationTokenFounded);
    }

    @Override
    public boolean isUserEnabledByToken(String token) {
       try{
           User userFound = userRepository.findUserByToken(token);
           if(Objects.nonNull(userFound)){
               return userFound.getEnabled();
           } else {
               throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
           }

       }catch (Exception e){
           throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
       }
    }

    @Override
    public Map<String, Object> changePassword(ResetPassword resetPassword) {
        Map<String, Object> response = new HashMap<>();
        if(this.isValidTokenToResetPassword(resetPassword.getToken())){
            User userFound = userRepository.findUserByToken(resetPassword.getToken());
            if(Objects.nonNull(userFound)){
                if(Boolean.TRUE.equals(userFound.getEnabled())){
                    userFound.setPassword(cryptPasswordEncoder.encode(resetPassword.getNewPassword()));
                    userFound.setLoginAttempts(0);
                    userRepository.save(userFound);
                    response.put("passwordChanged", "Password changed successfully");
                } else {
                    response.put("userNotEnabled", "Password changed successfully");
                }

            } else {
                response.put("userNotFound", "User not found");
            }

        } else {
            response.put("invalidToken", "Invalid token");
        }
        return response;

    }

    private boolean isTokenExpired(VerificationToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

}
