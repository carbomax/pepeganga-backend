package uy.pepeganga.meli.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.MeliOrders;
import uy.pepeganga.meli.service.models.dto.OrdersByDateCreatedAndCountDto;
import uy.pepeganga.meli.service.models.dto.IBetterSkuDto;
import uy.pepeganga.meli.service.models.dto.ISalesAndAmountBySeller;

import java.util.List;

public interface OrdersRepository extends JpaRepository<MeliOrders, Long> {

    @Transactional(readOnly = true)
    boolean existsByOrderId(String orderId);

    @Transactional(readOnly = true)
    MeliOrders findByOrderId(String orderId);

    @Transactional(readOnly = true)
    @Query(value = "select * from meli_orders where sent_to_erp = 0  limit :quantity", nativeQuery = true)
    List<MeliOrders> findAllBySentToErp(int quantity);

    @Transactional(readOnly = true)
    @Query(value = "select o.* from meli_orders o join meli_order_seller s join meli_order_buyer b where o.seller_id = s.id and s.seller_id in(:accounts) and o.buyer_id = b.id and o.status in (:statusFilter) and o.operator_business_status in(:operatorBssStatus) and b.first_name like %:nameClient% and s.firsts_name like %:nameSeller% and o.business_date_created >= :dateFrom and o.business_date_created <= :dateTo order by o.id desc",
            countQuery = "select count(*) from meli_orders", nativeQuery = true)
    Page<MeliOrders> findBySellerId(List<String> accounts, List<String> statusFilter, String nameClient, String nameSeller,  Long dateFrom, Long dateTo, List<Integer> operatorBssStatus,  Pageable pageable);


    @Transactional(readOnly = true)
    @Query(value = "select o.* from meli_orders o join meli_order_seller s join meli_order_buyer b where o.seller_id = s.id  and o.buyer_id = b.id and o.status in (:statusFilter) and o.operator_business_status in(:operatorBssStatus) and b.first_name like %:nameClient% and s.firsts_name like %:nameSeller% and o.business_date_created >= :dateFrom and o.business_date_created <= :dateTo order by o.id desc",
            countQuery = "select count(*) from meli_orders", nativeQuery = true)
    Page<MeliOrders> findAllOrders( List<String> statusFilter, String nameClient,  String nameSeller, Long dateFrom, Long dateTo, List<Integer> operatorBssStatus,  Pageable pageable);


    @Query(value = "select count(*) as count, business_date_created as dateCreatedBss, date_created as dateCreated from meli_orders o, meli_order_item i, selleraccount a, meli_order_seller s  where o.id = i.meli_orders_id and o.seller_id = s.id and a.user_id_bss = s.seller_id and o.business_date_created >= :dateFrom and o.business_date_created <= :dateTo and o.status = 'paid' group by o.business_date_created, o.date_created order by o.business_date_created", nativeQuery = true)
    List<OrdersByDateCreatedAndCountDto> getSalesByBusinessDateCreated(Long dateFrom, Long dateTo);

    @Query(value = "select count(*) as count, business_date_created as dateCreatedBss, date_created as dateCreated from meli_orders o, meli_order_item i, selleraccount a, meli_order_seller s  where o.id = i.meli_orders_id and o.seller_id = s.id and a.user_id_bss = s.seller_id and s.seller_id = :sellerId and o.business_date_created >= :dateFrom and o.business_date_created <= :dateTo and o.status = 'paid' group by o.business_date_created, o.date_created order by o.business_date_created", nativeQuery = true)
    List<OrdersByDateCreatedAndCountDto> getSalesByBusinessDateCreated(Long dateFrom, Long dateTo, Long sellerId);

    @Query(value = "select count(*) from meli_orders o, meli_order_seller s, meli_order_item i, selleraccount a where o.id = i.meli_orders_id and o.status = 'paid' and o.business_date_created between 0 and 999999999 and o.seller_id = s.id and a.user_id_bss = s.seller_id", nativeQuery = true)
    Long getCountAllSalesPaid();

    @Query(value = "select count(*) from meli_orders o, meli_order_seller s, meli_order_item i, selleraccount a where o.id = i.meli_orders_id and o.status = 'paid' and o.business_date_created between 0 and 999999999 and o.seller_id = s.id and a.user_id_bss = s.seller_id and a.user_id_bss = :sellerId", nativeQuery = true)
    Long getCountAllSalesPaid(Long sellerId);

    @Query(value = "select count(*) from meli_orders o, meli_order_seller s, meli_order_item i, selleraccount a where o.id = i.meli_orders_id and o.status = 'cancelled' and o.business_date_created between 0 and 999999999 and o.seller_id = s.id and a.user_id_bss = s.seller_id", nativeQuery = true)
    Long getCountAllSalesCancelled();

    @Query(value = "select count(*) from meli_orders o, meli_order_seller s, meli_order_item i, selleraccount a where o.id = i.meli_orders_id and o.status = 'cancelled' and o.business_date_created between 0 and 999999999 and o.seller_id = s.id and a.user_id_bss = s.seller_id and a.user_id_bss = :sellerId", nativeQuery = true)
    Long getCountAllSalesCancelled(Long sellerId);

    @Query(value = "select sum(i.quantity) as count, i.seller_sku as sku from meli_order_item i, meli_orders o, meli_order_seller s where i.meli_orders_id = o.id and o.status = 'paid' and o.seller_id = s.id and i.seller_sku is not null group by i.seller_sku order by count desc limit 1", nativeQuery = true)
    IBetterSkuDto getBetterSku();

    @Query(value = "select sum(i.quantity) as count, i.seller_sku as sku from meli_order_item i, meli_orders o, meli_order_seller s where i.meli_orders_id = o.id and o.status = 'paid' and o.seller_id = s.id and s.seller_id = :sellerId and i.seller_sku is not null group by i.seller_sku order by count desc limit 1", nativeQuery = true)
    IBetterSkuDto getBetterSku(Long sellerId);

    @Query(value = "select css.sum as count, css.seller_sku as sku, css.art_descrip_catalogo as name from (select sum(i.quantity) as sum, i.seller_sku, it.art_descrip_catalogo from meli_order_item i, meli_orders o,  item it where i.meli_orders_id = o.id and o.status = 'paid' and i.seller_sku is not null and it.sku = i.seller_sku group by i.seller_sku) as css order by count desc limit :size", nativeQuery = true)
    List<IBetterSkuDto> getBettersSku(Integer size);

    @Query(value = "select css.sum as count, css.seller_sku as sku, css.art_descrip_catalogo as name from (select sum(i.quantity) as sum, i.seller_sku, it.art_descrip_catalogo from meli_order_item i, meli_orders o, meli_order_seller s, item it where i.meli_orders_id = o.id and o.seller_id = s.id and o.status = 'paid' and i.seller_sku is not null and it.sku = i.seller_sku and s.seller_id = :sellerId group by i.seller_sku) as css order by count desc limit :size", nativeQuery = true)
    List<IBetterSkuDto> getBettersSku(Integer size, Long sellerId);

    @Query(value = "select sum(o.paid_amount) as amount,\n" +
            "       count(s.seller_id) as salesCount,\n" +
            "       s.seller_id        as sellerId,\n" +
            "       a.business_name    as sellerName\n" +
            "from meli_orders o,\n" +
            "     meli_order_seller s,\n" +
            "     selleraccount a\n" +
            "where o.seller_id = s.id\n" +
            "  and s.seller_id = a.user_id_bss\n" +
            "  and o.status = 'paid'\n" +
            "  and o.business_date_created between :dateFrom and :dateTo\n" +
            "group by s.seller_id, a.business_name", nativeQuery = true)
    List<ISalesAndAmountBySeller> getSalesAndAmountSellerByDate(long dateFrom, long dateTo);

    @Query(value = "select sum(o.paid_amount) as amount,\n" +
            "       count(s.seller_id) as salesCount,\n" +
            "       s.seller_id        as sellerId,\n" +
            "       a.business_name    as sellerName\n" +
            "from meli_orders o,\n" +
            "     meli_order_seller s,\n" +
            "     selleraccount a\n" +
            "where o.seller_id = s.id\n" +
            "  and s.seller_id = a.user_id_bss\n" +
            "  and a.user_id_bss = :sellerId\n" +
            "  and o.status = 'paid'\n" +
            "  and o.business_date_created between :dateFrom and :dateTo\n" +
            "group by s.seller_id, a.business_name", nativeQuery = true)
    List<ISalesAndAmountBySeller> getSalesAndAmountSellerByDate(long dateFrom, long dateTo, Long sellerId);
}
