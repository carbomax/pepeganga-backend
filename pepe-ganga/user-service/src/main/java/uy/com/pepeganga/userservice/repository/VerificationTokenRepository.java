package uy.com.pepeganga.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uy.com.pepeganga.business.common.entities.User;
import uy.com.pepeganga.userservice.entities.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

        boolean existsByUser(User user);

        @Query(value = "select * from verification_token v where v.user_id = :id ", nativeQuery = true)
        VerificationToken findByUser(Integer id);

        VerificationToken findByToken(String token);
}
