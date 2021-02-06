package uy.com.pepeganga.consumingwsstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.models.OrderDto;
import uy.com.pepeganga.consumingwsstore.client.MeliFeignClient;

import java.util.List;

@Service
public class MeliServiceClient implements IMeliServiceClient {


    @Autowired
    MeliFeignClient meliClient;

    @Autowired
    IPurchaseOrders po;

    @Override
    public List<OrderDto> getRecentOrdersByBatch(int quantity) {

        return meliClient.getRecentOrdersByBatch(quantity);
    }

    @Override
    public void executePurchaseOrder(){
        List<OrderDto> orderList = getRecentOrdersByBatch(3);
        if(!orderList.isEmpty())
            po.registerPurchaseOrders(orderList);
    }
}
