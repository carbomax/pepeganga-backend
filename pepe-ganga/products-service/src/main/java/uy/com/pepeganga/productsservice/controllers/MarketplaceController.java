package uy.com.pepeganga.productsservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping("/marketplaces/{id}")
    public ResponseEntity<Marketplace> updateMarketplace(@RequestBody Marketplace marketplace, @PathVariable Short id){
        return new ResponseEntity<>(marketplaceService.updateMarketplace(marketplace, id), HttpStatus.OK);
    }

    @PostMapping("/marketplaces")
    public ResponseEntity<Marketplace> createMarketplace(@RequestBody Marketplace marketplace){
        return new ResponseEntity<>(marketplaceService.createMarketplace(marketplace), HttpStatus.CREATED);
    }

    @DeleteMapping("/marketplaces/{id}")
    public ResponseEntity<Void> deleteMarketplace(@PathVariable Short id){
        marketplaceService.deleteMarketplace(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
