package uy.pepeganga.meli.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.com.pepeganga.business.common.entities.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {


}
