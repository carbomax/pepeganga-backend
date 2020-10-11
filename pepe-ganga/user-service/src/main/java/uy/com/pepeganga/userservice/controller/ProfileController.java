package uy.com.pepeganga.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.com.pepeganga.business.common.models.AuthAddInformationClaim;
import uy.com.pepeganga.userservice.service.IProfileService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProfileController {

    private final IProfileService profileService;

    public ProfileController(IProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profiles")
    public ResponseEntity<List<Profile>> getProfiles() {
        return new ResponseEntity<>(profileService.getProfiles(), HttpStatus.OK);
    }

    @GetMapping("/profiles/by-user-email/{email}")
    public ResponseEntity<AuthAddInformationClaim> findProfileByUserEmail(@PathVariable String email) {
        Profile profileFounded = profileService.findProfileByUserEmail(email);
        AuthAddInformationClaim claim = new AuthAddInformationClaim(profileFounded.getFirstName(), profileFounded.getLastName(), profileFounded.getId());
        return new ResponseEntity<>(claim, HttpStatus.OK);
    }
}
