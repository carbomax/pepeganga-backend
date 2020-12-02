package uy.com.pepeganga.productsservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uy.com.pepeganga.business.common.entities.CheckingStockProcessor;

public interface CheckingStockProcessorRepository extends JpaRepository<CheckingStockProcessor, Integer> {

    boolean existsBySku(String sku);

    CheckingStockProcessor findBySku(String sku);

}
