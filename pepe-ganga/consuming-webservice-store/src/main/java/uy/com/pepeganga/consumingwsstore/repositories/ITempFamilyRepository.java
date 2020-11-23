package uy.com.pepeganga.consumingwsstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.com.pepeganga.consumingwsstore.Entities.TempFamily;

public interface ITempFamilyRepository extends JpaRepository<TempFamily, Integer> {
}
