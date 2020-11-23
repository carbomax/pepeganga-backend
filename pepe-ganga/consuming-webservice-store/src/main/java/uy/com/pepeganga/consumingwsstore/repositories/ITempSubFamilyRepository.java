package uy.com.pepeganga.consumingwsstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.com.pepeganga.consumingwsstore.Entities.TempSubFamily;

public interface ITempSubFamilyRepository extends JpaRepository<TempSubFamily, Integer> {
}
