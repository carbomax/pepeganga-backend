package uy.com.pepeganga.consumingwsstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.com.pepeganga.consumingwsstore.Entities.TempBrand;

public interface ITempBrandRepository extends JpaRepository<TempBrand, Integer> {
}
