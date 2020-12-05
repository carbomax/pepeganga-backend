package uy.com.pepeganga.consumingwsstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.com.pepeganga.business.common.entities.CheckingStockProcessor;

@Repository
public interface CheckingStockProcessorRepository extends JpaRepository<CheckingStockProcessor, Integer> {
    public CheckingStockProcessor findBySku(String sku);
}
