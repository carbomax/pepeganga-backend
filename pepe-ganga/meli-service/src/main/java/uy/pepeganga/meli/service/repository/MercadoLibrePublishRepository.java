package uy.pepeganga.meli.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.com.pepeganga.business.common.entities.MercadoLibrePublications;

import java.util.List;

@Repository
public interface MercadoLibrePublishRepository extends JpaRepository<MercadoLibrePublications, Integer> {
    List<MercadoLibrePublications> findAllBySku(String sku);
}
