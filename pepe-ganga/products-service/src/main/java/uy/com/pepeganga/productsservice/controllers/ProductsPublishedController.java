package uy.com.pepeganga.productsservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.pepeganga.productsservice.gridmodels.DMDetailsPublicationsMeli;
import uy.com.pepeganga.productsservice.gridmodels.PageDeatilsPublicationMeli;
import uy.com.pepeganga.productsservice.services.MercadoLibrePublishService;

import java.util.List;

@RestController
@RequestMapping("/api/published")
public class ProductsPublishedController {

    @Autowired
    MercadoLibrePublishService publishService;


    @GetMapping("/{profileId}")
    public ResponseEntity<PageDeatilsPublicationMeli> getPublicationsDetailsBySellerProfile(@PathVariable Integer profileId, @RequestParam String sku,
                                                                                            @RequestParam String idMeliPublication, @RequestParam int meliAccount,
                                                                                            @RequestParam String typeStateSearch, @RequestParam int page, @RequestParam int size){
        return new ResponseEntity<>(publishService.getPublicationsDetailsBySellerProfile( profileId, sku, idMeliPublication, meliAccount, typeStateSearch, page, size), HttpStatus.OK);
    }
}
