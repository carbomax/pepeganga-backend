package uy.com.pepeganga.productsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.DetailsPublicationsMeli;
import uy.com.pepeganga.business.common.entities.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

}
