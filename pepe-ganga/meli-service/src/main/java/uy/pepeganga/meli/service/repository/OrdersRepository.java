package uy.pepeganga.meli.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.MeliOrders;

import java.util.List;

public interface OrdersRepository extends JpaRepository<MeliOrders, Long> {

    @Transactional(readOnly = true)
    boolean existsByOrderId(String orderId);

    @Transactional(readOnly = true)
    MeliOrders findByOrderId(String orderId);

    @Transactional(readOnly = true)
    @Query(value = "select o.* from meli_orders o join meli_order_seller s join meli_order_buyer b where o.seller_id = s.id and s.seller_id in(:accounts) and o.buyer_id = b.id and o.status in (:statusFilter) and b.first_name like %:nameClient% and o.business_date_created >= :dateFrom and o.business_date_created <= :dateTo",
            countQuery = "select count(*) from meli_orders", nativeQuery = true)
    Page<MeliOrders> findBySellerId(List<String> accounts, List<String> statusFilter, String nameClient,  Long dateFrom, Long dateTo, Pageable pageable);


}
