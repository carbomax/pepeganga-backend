package uy.com.pepeganga.consumingwsstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.com.pepeganga.business.common.entities.UpdatesOfSystem;

@Repository
public interface IUpdatesSystemRepository extends JpaRepository<UpdatesOfSystem, Long> {
}
