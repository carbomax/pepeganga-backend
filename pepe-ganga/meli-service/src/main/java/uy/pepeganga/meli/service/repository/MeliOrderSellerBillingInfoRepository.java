package uy.pepeganga.meli.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.com.pepeganga.business.common.entities.MeliOrderBuyerBillingInfo;

public interface MeliOrderSellerBillingInfoRepository extends JpaRepository<MeliOrderBuyerBillingInfo, Long > {
}
