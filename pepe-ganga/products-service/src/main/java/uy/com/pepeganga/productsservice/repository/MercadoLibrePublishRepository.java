package uy.com.pepeganga.productsservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import uy.com.pepeganga.business.common.entities.MercadoLibrePublications;

public interface MercadoLibrePublishRepository extends JpaRepository<MercadoLibrePublications, Short> {
    
	MercadoLibrePublications findByUser(String idUser);
	
	@Query("select ml.states from mercadolibrepublications ml where ml.user_id = ?1 ")
	List<String> findAllStatesByUser(String idUser);
}
