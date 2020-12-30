package uy.pepeganga.meli.service.services;

import uy.pepeganga.meli.service.models.dto.IBetterSkuDto;
import uy.pepeganga.meli.service.models.dto.OrdersByDateCreatedAndCountDto;
import uy.pepeganga.meli.service.models.dto.StockVsTotalItemDto;

import java.util.List;
import java.util.Map;

public interface IStatisticService {

    Long getCountAllSales();

    List<OrdersByDateCreatedAndCountDto> getSalesByBusinessDateCreated(Long dateFrom, Long dateTo);

    Long getCountActivePublications();

    IBetterSkuDto getBetterSku();

    // Map<stock, total>
    StockVsTotalItemDto getStockVsTotalOfItems();
}
