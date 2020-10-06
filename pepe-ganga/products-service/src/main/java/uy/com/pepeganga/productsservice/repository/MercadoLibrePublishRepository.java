package uy.com.pepeganga.productsservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import uy.com.pepeganga.business.common.entities.MercadoLibrePublications;

@Repository
public interface MercadoLibrePublishRepository extends JpaRepository<MercadoLibrePublications, Object> { 
	
	@Query(value = "select ml.states from mercadolibrepublications ml where ml.profile_id = ?1", nativeQuery = true)
	List<Short> findAllStatesByIdProfile(int profile_id);

	@Query(value = "select ml.item_id from mercadolibrepublications ml where ml.user_id = ?1 and ml.item_id = ?2", nativeQuery = true)
	String findProductSelectedByUser(int user_id, String item_id);

	@Query(value = "select COUNT(ml.item_id) from mercadolibrepublications ml where ml.profile_id = ?1 and ml.item_id = ?2", nativeQuery = true)
	Integer cantProductSelectedByProfile(int profile_id, String item_id);
}
