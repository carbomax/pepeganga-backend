package uy.pepeganga.meli.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.com.pepeganga.business.common.entities.MeliOrderBuyer;

public interface MeliOrderBuyerRepository extends JpaRepository<MeliOrderBuyer, Long> {

    MeliOrderBuyer findByBuyerId(Long buyerId);
}
