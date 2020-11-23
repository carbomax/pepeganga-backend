package uy.com.pepeganga.consumingwsstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.com.pepeganga.consumingwsstore.Entities.TempItem;

@Repository
public interface ITempItemRepository extends JpaRepository<TempItem, Integer> {
}
