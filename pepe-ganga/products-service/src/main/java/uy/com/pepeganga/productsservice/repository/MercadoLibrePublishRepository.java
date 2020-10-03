package uy.com.pepeganga.productsservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import uy.com.pepeganga.business.common.entities.MercadoLibrePublications;

@Repository
public interface MercadoLibrePublishRepository extends JpaRepository<MercadoLibrePublications, Object> { 
	
	@Query(value = "select ml.states from mercadolibrepublications ml where ml.user_id = ?1", nativeQuery = true)
	List<Short> findAllStatesByIdUser(int user_id);

	@Query(value = "select ml.item_id from mercadolibrepublications ml where ml.user_id = ?1 and ml.item_id = ?2", nativeQuery = true)
	List<String> findAllProductSelectedByUser(int user_id, String item_id);
}
