package uy.com.pepeganga.userservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import uy.com.pepeganga.userservice.entities.Profile;
import uy.com.pepeganga.userservice.entities.User;
import uy.com.pepeganga.userservice.models.UserFaker;
import uy.com.pepeganga.userservice.repository.ProfileRepository;
import uy.com.pepeganga.userservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final ProfileRepository profileRepository;


    private List<UserFaker> users = new ArrayList<>();

    public UserService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public List<UserFaker> getUsersFakers() {
        return this.users;
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
        User userSaved = userRepository.save(profile.getUser());
        profile.setUser(userSaved);
        return profileRepository.save(profile);
    }

    @Override
    public Profile updateUserProfile(Profile profile, Integer profileId, Integer userId) {

        Optional<Profile> profileToUpdatedDb = profileRepository.findById(profileId);
        Optional<User> userToUpdatedDb = userRepository.findById(userId);
        if (profileToUpdatedDb.isPresent() && userToUpdatedDb.isPresent()) {
            User userToUpdate = userToUpdatedDb.get();
            userToUpdate.setEmail(profile.getUser().getEmail());
            userToUpdate.setPassword(profile.getUser().getPassword());
            userToUpdate.setRoles(profile.getUser().getRoles());
            userToUpdate.setMarketplaces(profile.getUser().getMarketplaces());
            User userUpdated = userRepository.save(userToUpdate);
            profileToUpdatedDb.get().setFirstName(profile.getFirstName());
            profileToUpdatedDb.get().setLastName(profile.getLastName());
            profileToUpdatedDb.get().setUser(userUpdated);
            return profileRepository.save(profileToUpdatedDb.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User not updated with id %s", profile.getUser().getId()));
        }

    }


    @Override
    public Page<User> getUsersPage(User filter, int page, int size) {
        return userRepository.findAllByEmail("test", PageRequest.of(page, size));
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


}
