package uy.com.pepeganga.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uy.com.pepeganga.business.common.entities.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    void deleteProfileByUserId(Integer id);

    @Query(value = "select p.* from profile p join users u where p.user_id = u.id and u.email = :email", nativeQuery = true)
    Profile findProfileByUserEmail(@Param("email") String email);
}
