package uy.pepeganga.meli.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.com.pepeganga.business.common.entities.StockProcessor;

public interface StockProcessorRepository extends JpaRepository<StockProcessor, Long> {

    StockProcessor findBySku(String sku);
}
