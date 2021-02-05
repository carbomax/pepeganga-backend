package uy.com.pepeganga.consumingwsstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.MeliOrders;

import java.util.List;

@Repository
public interface IMeliOrderRepository extends JpaRepository<MeliOrders, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update MeliOrders mo set mo.sentToErp = 1 where mo.orderId in (:orderIds) ")
    void updateFieldSentToERPToAll(List<String> orderIds, int value);
}
