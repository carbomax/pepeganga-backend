package uy.com.pepeganga.consumingwsstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.consumingwsstore.entities.TempItem;

@Repository
public interface ITempItemRepository extends JpaRepository<TempItem, String> {

    @Query(value = "delete from tempitem_tempcategory", nativeQuery = true)
    void deleteAllCategoriesRelation();

    @Query(value = "select count(*) from tempitem_tempcategory", nativeQuery = true)
    Long getCountCategoriesRelation();
}
