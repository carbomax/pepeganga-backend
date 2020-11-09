package uy.com.pepeganga.userservice.service;

import org.springframework.data.jpa.repository.Query;
import uy.com.pepeganga.business.common.entities.Profile;

import java.util.List;

public interface IProfileService {

    List<Profile> getProfiles();

    Profile findProfileByUserEmail(String email);
}
