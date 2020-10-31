package uy.pepeganga.meli.service.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.MeliOrders;

import java.util.List;

public interface OrdersRepository extends JpaRepository<MeliOrders, Long> {

    @Transactional(readOnly = true)
    boolean existsByOrderId(String orderId);

    @Transactional(readOnly = true)
    MeliOrders findByOrderId(String orderId);

    @Transactional(readOnly = true)
    @Query(value = "select o.* from meli_orders o join meli_order_seller s where o.seller_id = s.id and s.seller_id in(:accounts) ", nativeQuery = true)
    List<MeliOrders> findBySellerId(List<String> accounts, Pageable pageable);

}
