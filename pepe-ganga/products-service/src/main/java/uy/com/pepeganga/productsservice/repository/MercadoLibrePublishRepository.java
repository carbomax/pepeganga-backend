package uy.com.pepeganga.productsservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import uy.com.pepeganga.business.common.entities.MercadoLibrePublications;

@Repository
public interface MercadoLibrePublishRepository extends JpaRepository<MercadoLibrePublications, Short> { 
	/*
	@Query("select ml.states from mercadolibrepublications ml where ml.user_id = ?1")
	List<String> findAllStatesByIdUser(Integer idUser);
*/
}
