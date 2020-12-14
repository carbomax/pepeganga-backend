package uy.com.pepeganga.userservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import uy.com.pepeganga.business.common.entities.User;


public interface UserRepository  extends JpaRepository<User, Integer>{


	Page<User> findAllByEmail(String email, Pageable pageable);

	boolean existsByEmail(String email);

	User findByEmail(String email);

	@Query(value = "select * from users u, verification_token v where v.user_id = u.id", nativeQuery = true)
	User findUserByToken(String token);
}
