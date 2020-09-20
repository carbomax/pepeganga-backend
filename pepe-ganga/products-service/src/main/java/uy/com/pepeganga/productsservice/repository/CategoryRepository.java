package uy.com.pepeganga.productsservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uy.com.pepeganga.productsservice.entities.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Short>{

}
