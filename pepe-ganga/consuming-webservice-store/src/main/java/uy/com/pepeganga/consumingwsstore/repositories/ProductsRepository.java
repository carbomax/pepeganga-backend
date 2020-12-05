package uy.com.pepeganga.consumingwsstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.MercadoLibrePublications;

@Repository
public interface ProductsRepository extends JpaRepository<MercadoLibrePublications, Integer> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update MercadoLibrePublications ml set ml.currentStock =:currentStock where ml.sku =:sku ")
    void updateStockBySKU(long currentStock, String sku);
}

