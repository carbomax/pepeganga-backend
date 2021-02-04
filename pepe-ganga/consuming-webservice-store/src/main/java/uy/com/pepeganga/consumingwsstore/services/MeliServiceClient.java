package uy.com.pepeganga.consumingwsstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import uy.com.pepeganga.business.common.models.OrderDto;

import java.util.List;

public class MeliServiceClient implements IMeliServiceClient {


    @Autowired
    MeliServiceClient meliClient;
    
    @Override
    public List<OrderDto> getRecentOrdersByBatch(int quantity) {

        return null;
    }
}
