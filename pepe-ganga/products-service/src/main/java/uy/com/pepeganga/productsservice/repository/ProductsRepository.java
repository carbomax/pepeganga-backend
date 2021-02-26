package uy.com.pepeganga.productsservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uy.com.pepeganga.business.common.entities.Item;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Item, String>{
	
	 Page<Item> findAll(Specification<Item> specification, Pageable pageable);

	 @Query(value = "select item.sku from item, mercadolibrepublications where item.sku = mercadolibrepublications.sku and profile_id = :profileId",
			 nativeQuery = true)
	 List<String> getSkusInToPublish(Integer profileId);

	@Query(value = "select i.sku from item i where i.sku not in  (select item.sku from item, mercadolibrepublications where item.sku = mercadolibrepublications.sku and profile_id = :profileId)",
			nativeQuery = true)
	List<String> getSkusNotInToPublish(Integer profileId);

}
