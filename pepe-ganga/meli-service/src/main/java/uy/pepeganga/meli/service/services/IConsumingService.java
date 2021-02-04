package uy.pepeganga.meli.service.services;


import uy.com.pepeganga.business.common.models.OrderDto;

import java.util.List;

public interface IConsumingService {

    String processPurchases(List<OrderDto> ordersDto);

}
