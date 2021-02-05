package uy.com.pepeganga.consumingwsstore.services;

import uy.com.pepeganga.business.common.models.OrderDto;

import java.util.List;

public interface IPurchaseOrders {
    public void registerPurchaseOrders(List<OrderDto> ordersDto);
}
