package uy.pepeganga.meli.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.models.OrderDto;
import uy.pepeganga.meli.service.clients.ConsumingFeignClient;

import java.util.List;

@Service
public class ConsumingService implements IConsumingService{

    @Autowired
    ConsumingFeignClient consumingClient;


    @Override
    public String processPurchases(List<OrderDto> ordersDto) {
        return consumingClient.processPurchases(ordersDto);
    }
}
