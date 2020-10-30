package uy.pepeganga.meli.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.com.pepeganga.business.common.entities.MeliOrderSeller;

public interface MeliOrderSellerRepository extends JpaRepository<MeliOrderSeller, Long> {
}
