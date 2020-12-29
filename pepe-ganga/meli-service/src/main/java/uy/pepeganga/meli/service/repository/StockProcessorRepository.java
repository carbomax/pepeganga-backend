package uy.pepeganga.meli.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uy.com.pepeganga.business.common.entities.StockProcessor;

public interface StockProcessorRepository extends JpaRepository<StockProcessor, Long> {

    StockProcessor findBySku(String sku);

    @Query(value = "select count(*) from stockprocessor s where s.real_stock >= 0", nativeQuery = true)
    Integer countAll();

    @Query(value = "select count(*) from stockprocessor s where s.real_stock > 0", nativeQuery = true)
    Integer countWithStock();
}
