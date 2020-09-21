package uy.com.pepeganga.productsservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.com.pepeganga.business.common.entities.Marketplace;
import uy.com.pepeganga.productsservice.services.MarketplaceService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MarketplaceController {

    private final MarketplaceService marketplaceService;

    public MarketplaceController(MarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;
    }

    @GetMapping("/marketplaces")
    public ResponseEntity<List<Marketplace>> getMarketplaces(){
        return new ResponseEntity<>(marketplaceService.getMarketplaces(), HttpStatus.OK);
    }
}
