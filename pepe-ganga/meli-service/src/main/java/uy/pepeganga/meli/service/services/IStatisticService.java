package uy.pepeganga.meli.service.services;

import uy.pepeganga.meli.service.models.dto.IBetterSkuDto;
import uy.pepeganga.meli.service.models.dto.OrdersByDateCreatedAndCountDto;
import uy.pepeganga.meli.service.models.dto.StockVsTotalItemDto;

import java.util.List;

public interface IStatisticService {

    Long getCountAllSales();

    List<OrdersByDateCreatedAndCountDto> getSalesByBusinessDateCreated(Long dateFrom, Long dateTo);

    Long getCountActivePublications();

    IBetterSkuDto getBetterSku();

    List<IBetterSkuDto> getBettersSku(Integer size);

    // Map<stock, total>
    StockVsTotalItemDto getStockVsTotalOfItems();
}
