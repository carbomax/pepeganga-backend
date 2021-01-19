package uy.com.pepeganga.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.UpdatesOfSystem;

import java.util.Date;

@Repository
public interface IConfigurationSystemRepository extends JpaRepository<UpdatesOfSystem, Long> {

    @Transactional(readOnly = true)
    @Query(value = "select * from updatesofsystem us where us.id= (select max(id) from updatesofsystem)", nativeQuery = true)
    UpdatesOfSystem getLastData();
}
