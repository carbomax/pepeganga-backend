package uy.pepeganga.meli.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uy.com.pepeganga.business.common.entities.MercadoLibrePublications;

import java.util.List;

@Repository
public interface MercadoLibrePublishRepository extends JpaRepository<MercadoLibrePublications, Integer> { 
	
	@Query(value = "select ml.states from mercadolibrepublications ml where ml.profile_id = ?1", nativeQuery = true)
	List<Short> findAllStatesByIdProfile(int profile_id);

	@Query(value = "select ml.item_id from mercadolibrepublications ml where ml.profile_id = ?1 and ml.item_id = ?2", nativeQuery = true)
	String findIdItemForeignByProfileAndItem(int profile_id, String item_id);

	@Query(value = "select COUNT(ml.item_id) from mercadolibrepublications ml where ml.profile_id = ?1 and ml.item_id = ?2", nativeQuery = true)
	Integer cantProductSelectedByProfile(int profile_id, String item_id);

	Page<MercadoLibrePublications> findAll(Specification<MercadoLibrePublications> specification, Pageable pageable);
	
	@Query(value = "select * from mercadolibrepublications ml where ml.item_id = ?1 and ml.profile_id = ?2", nativeQuery = true)
	MercadoLibrePublications findByItemAndProfile(String item, Integer profile);
		
}
