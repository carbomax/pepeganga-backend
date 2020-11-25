package uy.pepeganga.meli.service.services;

import org.springframework.data.domain.Page;
import uy.com.pepeganga.business.common.entities.MeliOrders;

import java.util.List;
import java.util.Map;

public interface IOrderService {

    void schedulingOrdersV2();

    Map<String, Object> getOrdersByAccount(Integer accountId);

    Object delete();

    // Real implementation
    Page<MeliOrders> getAllOrdersByProfile(Integer profileId, List<String> statusFilter, String nameClient, Long dateFrom, Long dateTo, int page, int size);

    boolean updateCarrier(Long orderId, int carrierId);

    boolean updateOperatorName(Long orderId, String name);

    boolean updateTag(Long orderId, Integer tagBss);

    boolean updateObservation(Long orderId, String observation);

    boolean updateInvoice(Long id, Long orderId);

    Map<String, Object> getInvoiceUrl(Long orderId);
}
