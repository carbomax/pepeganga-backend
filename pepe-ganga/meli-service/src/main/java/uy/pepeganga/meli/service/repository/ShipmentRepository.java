package uy.pepeganga.meli.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.MeliOrderShipment;

public interface ShipmentRepository extends JpaRepository<MeliOrderShipment, Long> {

    @Transactional(readOnly = true)
    MeliOrderShipment findMeliOrderShipmentById(Long id);
}
