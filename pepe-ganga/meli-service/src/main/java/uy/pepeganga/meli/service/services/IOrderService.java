package uy.pepeganga.meli.service.services;

import uy.com.pepeganga.business.common.entities.MeliOrders;

import java.util.List;
import java.util.Map;

public interface IOrderService {

    void schedulingOrdersV2();

    Map<String, Object> getOrdersByAccount(Integer accountId);

    Object delete();

    // Real implementation
    List<MeliOrders> getAllOrdersByProfile(Integer profileId, int page, int size);

}
