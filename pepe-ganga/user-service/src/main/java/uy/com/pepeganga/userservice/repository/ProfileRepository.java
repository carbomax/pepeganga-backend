package uy.com.pepeganga.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.com.pepeganga.business.common.entities.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    void deleteProfileByUserId(Integer id);
}
