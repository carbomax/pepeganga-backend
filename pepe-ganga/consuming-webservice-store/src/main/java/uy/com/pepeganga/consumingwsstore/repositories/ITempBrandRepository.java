package uy.com.pepeganga.consumingwsstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.com.pepeganga.consumingwsstore.entities.TempBrand;

@Repository
public interface ITempBrandRepository extends JpaRepository<TempBrand, Short> {

}
