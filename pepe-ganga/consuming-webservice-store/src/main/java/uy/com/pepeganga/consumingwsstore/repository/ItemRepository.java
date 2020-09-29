package uy.com.pepeganga.consumingwsstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uy.com.pepeganga.business.common.entities.*;

@Repository
public interface ItemRepository extends JpaRepository<Item, String>{
		
	    Page<Item> findAll(Specification<Item> specification, Pageable pageable);

}
