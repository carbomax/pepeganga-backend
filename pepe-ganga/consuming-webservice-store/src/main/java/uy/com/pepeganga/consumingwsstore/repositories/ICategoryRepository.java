package uy.com.pepeganga.consumingwsstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uy.com.pepeganga.consumingwsstore.entities.Category;


@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer>{

}
