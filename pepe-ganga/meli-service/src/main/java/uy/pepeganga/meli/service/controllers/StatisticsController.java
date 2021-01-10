package uy.pepeganga.meli.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.pepeganga.meli.service.models.dto.*;
import uy.pepeganga.meli.service.services.IOrderService;
import uy.pepeganga.meli.service.services.IStatisticService;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    IOrderService orderService;

    @Autowired
    IStatisticService statisticService;

    @GetMapping("/all-sales-by-date-and-count")
    public ResponseEntity<List<OrdersByDateCreatedAndCountDto>> getSalesByBusinessDateCreated(@RequestParam(defaultValue = "0", required = false) Long dateFrom, @RequestParam(defaultValue = "99999999", required = false) Long dateTo ){
        return new ResponseEntity<>(statisticService.getSalesByBusinessDateCreated(dateFrom, dateTo), HttpStatus.OK);
    }

    @GetMapping("/count-all-sales")
    public ResponseEntity<Long> getCountAllSales(){
        return new ResponseEntity<>(statisticService.getCountAllSales(), HttpStatus.OK);
    }

    @GetMapping("/count-active-publications")
    public ResponseEntity<Long> getCountActivePublications(){
        return new ResponseEntity<>(statisticService.getCountActivePublications(), HttpStatus.OK);
    }

    @GetMapping("/better-sku")
    public ResponseEntity<IBetterSkuDto> getBetterSku(){
        return new ResponseEntity<>(statisticService.getBetterSku(), HttpStatus.OK);
    }

    @GetMapping("/better-sku/{size}")
    public ResponseEntity<List<IBetterSkuDto>> getBettersSku(@PathVariable Integer size){
        return new ResponseEntity<>(statisticService.getBettersSku(size), HttpStatus.OK);
    }

    @GetMapping("/stock-vs-total-items")
    public ResponseEntity<StockVsTotalItemDto> getStockVsTotalOfItems(){
        return new ResponseEntity<>(statisticService.getStockVsTotalOfItems(), HttpStatus.OK);
    }

    @GetMapping("/analysis-drop")
    public ResponseEntity<List<AnalysisDropDto>> getAnalysisDrop(@RequestParam List<String> dates){
        return new ResponseEntity<>(statisticService.getAnalysisDrop(dates), HttpStatus.OK);
    }

}
