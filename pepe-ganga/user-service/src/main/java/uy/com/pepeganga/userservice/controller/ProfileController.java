package uy.com.pepeganga.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.com.pepeganga.business.common.entities.Profile;
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
    public ResponseEntity<List<Profile>> getProfiles(){
        return new ResponseEntity<>(profileService.getProfiles(), HttpStatus.OK);
    }
}
