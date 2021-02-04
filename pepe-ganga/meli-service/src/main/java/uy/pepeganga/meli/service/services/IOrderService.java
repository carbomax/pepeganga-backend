package uy.pepeganga.meli.service.services;

import org.springframework.data.domain.Page;
import uy.com.pepeganga.business.common.entities.MeliOrders;
import uy.com.pepeganga.business.common.models.OrderDto;
import uy.pepeganga.meli.service.models.dto.CountPaidAndCancellerSalesDto;
import uy.pepeganga.meli.service.models.dto.IBetterSkuDto;
import uy.pepeganga.meli.service.models.dto.OrdersByDateCreatedAndCountDto;
import uy.pepeganga.meli.service.models.dto.ISalesAndAmountBySeller;

import java.util.List;
import java.util.Map;

public interface IOrderService {

    void schedulingOrdersV2();

    Map<String, Object> getOrdersByAccount(Integer accountId);

    Object delete();

    // Real implementation
    Page<MeliOrders> getAllOrdersByProfile(Integer profileId, List<String> statusFilter, String nameClient, String nameSeller, Long dateFrom, Long dateTo, int page, int size, List<String> operatorBusinessStatus);

    boolean updateCarrier(Long orderId, int carrierId);

    boolean updateOperatorName(Long orderId, String name);

    boolean updateTag(Long orderId, Integer tagBss);

    boolean updateObservation(Long orderId, String observation);

    boolean updateInvoice(Long id, Long orderId);

    Map<String, Object> getInvoiceUrl(Long orderId);

    boolean updateOperatorBusinessStatus(Long orderId, Integer status);

    List<OrdersByDateCreatedAndCountDto> getSalesByBusinessDateCreated(Long dateFrom, Long dateTo, Long sellerId);

    CountPaidAndCancellerSalesDto getCountAllSales(Long sellerId);

    IBetterSkuDto getBetterSku(Long sellerId);

    List<IBetterSkuDto> getBettersSku(Integer size, Long sellerId);

    List<ISalesAndAmountBySeller> getAnalysisDrop(long dateFrom, long dateTo, Long sellerId);

    List<OrderDto> getRecentOrdersByBatch(int quantity);
}
