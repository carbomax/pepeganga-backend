package uy.com.pepeganga.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.com.pepeganga.business.common.entities.User;
import uy.com.pepeganga.userservice.entities.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

        boolean existsByUser(User user);

        VerificationToken findByUser(User user);

        VerificationToken findByToken(String token);
}
