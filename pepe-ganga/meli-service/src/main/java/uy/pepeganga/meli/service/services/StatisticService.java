package uy.pepeganga.meli.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.pepeganga.meli.service.models.dto.IBetterSkuDto;
import uy.pepeganga.meli.service.models.dto.OrdersByDateCreatedAndCountDto;
import uy.pepeganga.meli.service.models.dto.StockVsTotalItemDto;
import uy.pepeganga.meli.service.repository.DetailsPublicationMeliRepository;
import uy.pepeganga.meli.service.repository.StockProcessorRepository;

import java.util.List;

@Service
public class StatisticService implements IStatisticService{

    @Autowired
    IOrderService orderService;

    @Autowired
    DetailsPublicationMeliRepository publicationMeliRepository;

    @Autowired
    StockProcessorRepository processorRepository;

    @Override
    public Long getCountAllSales() {
        return orderService.getCountAllSales();
    }

    @Override
    public List<OrdersByDateCreatedAndCountDto> getSalesByBusinessDateCreated(Long dateFrom, Long dateTo) {
        return orderService.getSalesByBusinessDateCreated(dateFrom, dateTo);
    }

    @Override
    public Long getCountActivePublications() {
        return publicationMeliRepository.getCountActivePublications();
    }

    @Override
    public IBetterSkuDto getBetterSku() {
        return orderService.getBetterSku();
    }

    @Override
    public List<IBetterSkuDto> getBettersSku(Integer size) {
        return orderService.getBettersSku(size);
    }

    @Override
    public StockVsTotalItemDto getStockVsTotalOfItems() {
        StockVsTotalItemDto dto = new StockVsTotalItemDto();
        dto.setTotal(processorRepository.countAll());
        dto.setWithStock(processorRepository.countWithStock());
       return dto;
    }
}
