package uy.com.pepeganga.consumingwsstore.services;

import uy.com.pepeganga.business.common.models.OrderDto;
import uy.com.pepeganga.business.common.models.ReasonResponse;

import java.util.List;

public interface IPurchaseOrdersService {
    List<ReasonResponse> registerPurchaseOrders(List<OrderDto> ordersDto);
}
