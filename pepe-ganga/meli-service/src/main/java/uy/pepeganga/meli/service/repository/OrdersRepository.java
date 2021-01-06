package uy.pepeganga.meli.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.MeliOrders;
import uy.pepeganga.meli.service.models.dto.OrdersByDateCreatedAndCountDto;
import uy.pepeganga.meli.service.models.dto.IBetterSkuDto;

import java.util.List;

public interface OrdersRepository extends JpaRepository<MeliOrders, Long> {

    @Transactional(readOnly = true)
    boolean existsByOrderId(String orderId);

    @Transactional(readOnly = true)
    MeliOrders findByOrderId(String orderId);

    @Transactional(readOnly = true)
    @Query(value = "select o.* from meli_orders o join meli_order_seller s join meli_order_buyer b where o.seller_id = s.id and s.seller_id in(:accounts) and o.buyer_id = b.id and o.status in (:statusFilter) and o.operator_business_status in(:operatorBssStatus) and b.first_name like %:nameClient% and o.business_date_created >= :dateFrom and o.business_date_created <= :dateTo order by o.id desc",
            countQuery = "select count(*) from meli_orders", nativeQuery = true)
    Page<MeliOrders> findBySellerId(List<String> accounts, List<String> statusFilter, String nameClient,  Long dateFrom, Long dateTo, List<Integer> operatorBssStatus,  Pageable pageable);


    @Transactional(readOnly = true)
    @Query(value = "select o.* from meli_orders o join meli_order_seller s join meli_order_buyer b where o.seller_id = s.id  and o.buyer_id = b.id and o.status in (:statusFilter) and o.operator_business_status in(:operatorBssStatus) and b.first_name like %:nameClient% and o.business_date_created >= :dateFrom and o.business_date_created <= :dateTo order by o.id desc",
            countQuery = "select count(*) from meli_orders", nativeQuery = true)
    Page<MeliOrders> findAllOrders( List<String> statusFilter, String nameClient,  Long dateFrom, Long dateTo, List<Integer> operatorBssStatus,  Pageable pageable);


    @Query(value = "select sum(i.quantity) as count, business_date_created as dateCreatedBss, date_created as dateCreated from meli_orders o, meli_order_item i  where o.id = i.meli_orders_id and o.business_date_created >= :dateFrom and o.business_date_created <= :dateTo and o.status = 'paid' group by o.business_date_created, o.date_created order by o.business_date_created", nativeQuery = true)
    List<OrdersByDateCreatedAndCountDto> getSalesByBusinessDateCreated(Long dateFrom, Long dateTo);

    @Query(value = "select sum(i.quantity) as count, business_date_created as dateCreatedBss, date_created as dateCreated from meli_orders o, meli_order_item i  where o.id = i.meli_orders_id and o.business_date_created >= 0 and o.business_date_created <= 99999999 and o.status = 'paid'", nativeQuery = true)
    Long getCountAllSales();

    @Query(value = "select max(css.sum) as count, css.seller_sku as sku from (select sum(i.quantity) as sum, i.seller_sku from meli_order_item i, meli_orders o where i.meli_orders_id = o.id and o.status = 'paid' and i.seller_sku is not null group by i.seller_sku) as css", nativeQuery = true)
    IBetterSkuDto getBetterSku();

    @Query(value = "select css.sum as count, css.seller_sku as sku, css.art_descrip_catalogo as name from (select sum(i.quantity) as sum, i.seller_sku, it.art_descrip_catalogo from meli_order_item i, meli_orders o, item it where i.meli_orders_id = o.id and o.status = 'paid' and i.seller_sku is not null and it.sku = i.seller_sku group by i.seller_sku) as css order by count desc limit :size", nativeQuery = true)
    List<IBetterSkuDto> getBettersSku(Integer size);
}
