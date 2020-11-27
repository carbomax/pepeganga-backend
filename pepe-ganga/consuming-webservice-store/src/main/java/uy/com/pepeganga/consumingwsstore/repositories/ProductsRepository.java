package uy.com.pepeganga.consumingwsstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.MercadoLibrePublications;

@Repository
public interface ProductsRepository extends JpaRepository<MercadoLibrePublications, Integer> {

    @Transactional
    @Query(value = "update mercadolibrepublications ml set current_stock = :currentStock where ml.sku = :sku ", nativeQuery = true)
    void updateStockBySKU(int currentStock, String sku);
}

