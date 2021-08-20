package uy.com.pepeganga.consumingwsstore.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.models.OrderDto;
import uy.com.pepeganga.consumingwsstore.client.MeliFeignClient;

import java.util.List;

@Service
public class MeliServiceClient implements IMeliServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(MeliServiceClient.class);

    @Autowired
    MeliFeignClient meliClient;

    @Autowired
    IPurchaseOrdersService po;

    @Override
    public List<OrderDto> getRecentOrdersByBatch(int quantity) {

        return meliClient.getRecentOrdersByBatch(quantity);
    }

    @Override
    public void executePurchaseOrder(){
        try {
            List<OrderDto> orderList = getRecentOrdersByBatch(10);
            if (!orderList.isEmpty())
                po.registerPurchaseOrders(orderList);
        }catch (Exception e){
            logger.error("Error: {}", e.getMessage());
        }
    }
}
