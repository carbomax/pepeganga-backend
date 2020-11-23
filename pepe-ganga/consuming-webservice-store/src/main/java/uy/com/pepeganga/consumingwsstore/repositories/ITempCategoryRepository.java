package uy.com.pepeganga.consumingwsstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.com.pepeganga.consumingwsstore.Entities.TempCategory;

public interface ITempCategoryRepository extends JpaRepository<TempCategory, Integer> {
}
