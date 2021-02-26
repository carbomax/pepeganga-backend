package uy.com.pepeganga.consumingwsstore.services;

import uy.com.pepeganga.business.common.models.OrderDto;

import java.util.List;

public interface IPurchaseOrdersService {
    void registerPurchaseOrders(List<OrderDto> ordersDto);
}
