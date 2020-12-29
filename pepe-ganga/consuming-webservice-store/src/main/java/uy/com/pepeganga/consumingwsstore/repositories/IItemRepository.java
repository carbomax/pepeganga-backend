package uy.com.pepeganga.consumingwsstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.*;

@Repository
public interface IItemRepository extends JpaRepository<Item, String>{
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Item i set i.updated =:updated ")
    void updateFieldUpdatedToAll(boolean updated);
}
