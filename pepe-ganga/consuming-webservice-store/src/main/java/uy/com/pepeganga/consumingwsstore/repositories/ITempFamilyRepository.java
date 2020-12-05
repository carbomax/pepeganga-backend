package uy.com.pepeganga.consumingwsstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.com.pepeganga.consumingwsstore.entities.TempFamily;

@Repository
public interface ITempFamilyRepository extends JpaRepository<TempFamily, Short> {
}
