package uy.pepeganga.meli.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.com.pepeganga.business.common.entities.MeliAccount;

@Repository
public interface MeliAccountRepository extends JpaRepository<MeliAccount, Integer> {
}
