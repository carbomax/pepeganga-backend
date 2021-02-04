package uy.com.pepeganga.consumingwsstore.services;

import uy.com.pepeganga.business.common.models.OrderDto;

import java.util.List;

public interface IMeliService {

    List<OrderDto> getRecentOrdersByBatch(int quantity);
}
