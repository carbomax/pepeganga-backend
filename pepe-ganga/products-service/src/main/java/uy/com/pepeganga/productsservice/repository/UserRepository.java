package uy.com.pepeganga.productsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uy.com.pepeganga.business.common.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
