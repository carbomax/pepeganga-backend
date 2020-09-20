package uy.com.pepeganga.productsservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uy.com.pepeganga.productsservice.entities.Item;

@Repository
public interface ProductsRepository extends JpaRepository<Item, String>{
	
	 Page<Item> findAll(Specification<Item> specification, Pageable pageable);

}
