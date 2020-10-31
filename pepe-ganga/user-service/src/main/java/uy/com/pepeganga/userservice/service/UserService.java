package uy.com.pepeganga.userservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.com.pepeganga.business.common.entities.User;
import uy.com.pepeganga.userservice.repository.ProfileRepository;
import uy.com.pepeganga.userservice.repository.UserRepository;

import java.util.List;
import java.util.Optional;


@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final ProfileRepository profileRepository;

    private final BCryptPasswordEncoder cryptPasswordEncoder;


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

        if(userRepository.existsByEmail(profile.getUser().getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("User with email: %s exist", profile.getUser().getEmail()));
        }
        Optional<Profile> profileToUpdatedDb = profileRepository.findById(profileId);
        Optional<User> userToUpdatedDb = userRepository.findById(userId);
        if (profileToUpdatedDb.isPresent() && userToUpdatedDb.isPresent()) {

                User userToUpdate = userToUpdatedDb.get();
                userToUpdate.setEmail(profile.getUser().getEmail());
                userToUpdate.setPassword(cryptPasswordEncoder.encode(profile.getUser().getPassword()));
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


}
