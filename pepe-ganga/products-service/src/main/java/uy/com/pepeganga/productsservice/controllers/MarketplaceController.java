package uy.com.pepeganga.productsservice.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.business.common.entities.Marketplace;
import uy.com.pepeganga.productsservice.gridmodels.MarketplaceDetails;
import uy.com.pepeganga.productsservice.services.MarketplaceService;

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
    
    @GetMapping("/marketplaces-details/{idUser}")
    public ResponseEntity<List<MarketplaceDetails>>getDetailsMarketplaces(@PathVariable("idUser") Integer idUser){
    	return new ResponseEntity<>(marketplaceService.getListDetailMarketplacesByUser(idUser), HttpStatus.OK);
    }
}
