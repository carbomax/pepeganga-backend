package uy.pepeganga.meli.service.services;

import com.google.common.util.concurrent.AtomicDouble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.entities.SellerAccount;
import uy.pepeganga.meli.service.models.dto.*;
import uy.pepeganga.meli.service.repository.DetailsPublicationMeliRepository;
import uy.pepeganga.meli.service.repository.SellerAccountRepository;
import uy.pepeganga.meli.service.repository.StockProcessorRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class StatisticService implements IStatisticService{

    @Autowired
    IOrderService orderService;

    @Autowired
    DetailsPublicationMeliRepository publicationMeliRepository;

    @Autowired
    StockProcessorRepository processorRepository;

    @Autowired
    SellerAccountRepository sellerAccountRepository;

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

    @Override
    public List<AnalysisDropDto> getAnalysisDrop(List<String> dates) {

        Map<String, Long> map =  new HashMap<>();
        dates.forEach(s ->  map.put(s,Long.parseLong(s.concat("01").replace("-", ""))));

        List<AnalysisDropDto> analysisDrops =  new ArrayList<>();
        List<SellerAccount> sellerAccounts = sellerAccountRepository.findAll().stream().filter(s -> s.getUserIdBss() != null).collect(Collectors.toList());
        map.forEach((key, value) -> {
            AnalysisDropDto analysisDropDto = new AnalysisDropDto();
            analysisDropDto.setDate(key);
            AtomicInteger totalSales = new AtomicInteger(0);
            AtomicDouble totalAmount = new AtomicDouble(0);
            List<SalesAndAmountBySellerDto> salesAndAmountBySeller= orderService.getAnalysisDrop(value, value + 30).stream()
                    .map( item -> {
                        SalesAndAmountBySellerDto salesAndAmountBySellerDto = new SalesAndAmountBySellerDto();
                        salesAndAmountBySellerDto.setSellerId(item.getSellerId());
                        salesAndAmountBySellerDto.setSellerName(item.getSellerName());
                        salesAndAmountBySellerDto.setSalesCount(item.getSalesCount());
                        salesAndAmountBySellerDto.setAmount(item.getAmount());
                        totalSales.addAndGet(salesAndAmountBySellerDto.getSalesCount());
                        totalAmount.addAndGet(salesAndAmountBySellerDto.getAmount());
                        return salesAndAmountBySellerDto;
                    } ).collect(Collectors.toList());
            sellerAccounts.forEach(sellerAccount -> {
                SalesAndAmountBySellerDto salesAndAmountBySellerDto = new SalesAndAmountBySellerDto();
                if(salesAndAmountBySeller.stream().noneMatch(item -> item.getSellerId() == sellerAccount.getUserIdBss())){
                    salesAndAmountBySellerDto.setSellerId(sellerAccount.getUserIdBss());
                    salesAndAmountBySellerDto.setSellerName(sellerAccount.getBusinessName());
                    salesAndAmountBySellerDto.setSalesCount(0);
                    salesAndAmountBySellerDto.setAmount(0);
                    salesAndAmountBySeller.add(salesAndAmountBySellerDto);
                }
            });

            salesAndAmountBySeller.sort(Comparator.comparing(SalesAndAmountBySellerDto::getSellerName));
            analysisDropDto.setSalesAndAmountBySeller(salesAndAmountBySeller);
            analysisDropDto.setTotalAmount(totalAmount.get());
            analysisDropDto.setTotalSalesCount(totalSales.get());
            analysisDrops.add(analysisDropDto);
        });

        return analysisDrops;
    }
}
