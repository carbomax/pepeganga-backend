package uy.com.pepeganga.productsservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.pepeganga.productsservice.gridmodels.DMDetailsPublicationsMeli;
import uy.com.pepeganga.productsservice.gridmodels.PageDeatilsPublicationMeli;
import uy.com.pepeganga.productsservice.models.EditableProductModel;
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
                                                                                            @RequestParam String typeStateSearch, @RequestParam String title, @RequestParam int page, @RequestParam int size){
        return new ResponseEntity<>(publishService.getPublicationsDetailsBySellerProfile( profileId, sku, idMeliPublication, meliAccount, typeStateSearch, title, page, size), HttpStatus.OK);
    }

    @PutMapping("/edit-publication-info")
    public ResponseEntity<DMDetailsPublicationsMeli> updateInfoOfPublication(@RequestBody DMDetailsPublicationsMeli product, @RequestParam List<Integer>imagesToDelete){
        try {
            return new ResponseEntity<>(publishService.editInfoOfPublication(product, imagesToDelete), HttpStatus.OK);
        }
        catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(product, HttpStatus.CONFLICT);
        }

    }

    @GetMapping("/publication-info/{id}")
    public ResponseEntity<DMDetailsPublicationsMeli> getDetailPublication(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(publishService.getDetailPublication(id), HttpStatus.OK);
        }
        catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>( HttpStatus.CONFLICT);
        }

    }

}
