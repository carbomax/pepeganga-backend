package uy.pepeganga.meli.service.services;

import uy.pepeganga.meli.service.models.dto.*;

import java.util.List;

public interface IStatisticService {

    CountPaidAndCancellerSalesDto getCountAllSales(Long sellerId);

    List<OrdersByDateCreatedAndCountDto> getSalesByBusinessDateCreated(Long dateFrom, Long dateTo, Long sellerId);

    Long getCountActivePublications(Long sellerId);

    IBetterSkuDto getBetterSku(Long sellerId);

    List<IBetterSkuDto> getBettersSku(Integer size, Long sellerId);

    // Map<stock, total>
    StockVsTotalItemDto getStockVsTotalOfItems();

    List<AnalysisDropDto> getAnalysisDrop(List<String> dates, Long sellerId);
}
