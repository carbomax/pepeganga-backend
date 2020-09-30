package uy.com.pepeganga.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.com.pepeganga.business.common.entities.Role;

public interface RoleRepository  extends JpaRepository<Role, Integer> {
}
