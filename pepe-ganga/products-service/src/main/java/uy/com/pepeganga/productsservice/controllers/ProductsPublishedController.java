package uy.com.pepeganga.productsservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.pepeganga.productsservice.gridmodels.DetailsPublicationsMeliGrid;
import uy.com.pepeganga.productsservice.services.MercadoLibrePublishService;

import java.util.List;

@RestController
@RequestMapping("/api/published")
public class ProductsPublishedController {

    @Autowired
    MercadoLibrePublishService publishService;


    @GetMapping("/{profileId}")
    public ResponseEntity<List<DetailsPublicationsMeliGrid>> getPublicationsDetailsBySellerProfile(@PathVariable Integer profileId, @RequestParam int page, @RequestParam int size){
        return new ResponseEntity<>(publishService.getPublicationsDetailsBySellerProfile(profileId, page, size), HttpStatus.OK);
    }
}
