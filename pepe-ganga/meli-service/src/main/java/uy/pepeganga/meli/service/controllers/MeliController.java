package uy.pepeganga.meli.service.controllers;

import meli.ApiException;
import meli.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.pepeganga.business.common.entities.SellerAccount;
import uy.pepeganga.meli.service.models.DetailsPublicationsMeliGrid;
import uy.pepeganga.meli.service.models.ItemModel;
import uy.pepeganga.meli.service.models.publications.ChangeMultipleStatusRequest;
import uy.pepeganga.meli.service.services.IMeliService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class MeliController {

    @Autowired
    IMeliService meliService;


    @PostMapping("/{profileId}")
    public ResponseEntity<SellerAccount> createAccountByProfile(@PathVariable Integer profileId, @RequestBody SellerAccount sellerAccount) {
        return new ResponseEntity<>(meliService.createAccountByProfile(profileId, sellerAccount), HttpStatus.CREATED);
    }


    @GetMapping("/by-profile/{profileId}")
    public ResponseEntity<List<SellerAccount>> meliAccountsByProfileId(@PathVariable Integer profileId) {
        return new ResponseEntity<>(meliService.meliAccountsByProfileId(profileId), HttpStatus.OK);
    }


    @PutMapping("/update/{accountId}")
    public ResponseEntity<SellerAccount> updateMeliAccount(@PathVariable Integer accountId, @RequestBody SellerAccount sellerAccount) {
        return new ResponseEntity<>(meliService.updateMeliAccount(accountId, sellerAccount), HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteMeliAccount(@PathVariable Integer accountId) {
        meliService.deleteMeliAccount(accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/publications/{accountId}")
    public ResponseEntity<Map<String, Object>> createPublication(@RequestBody Item item, @PathVariable Integer accountId){
        return new ResponseEntity<>(meliService.createPublication(item, accountId), HttpStatus.CREATED);
    }

    @PostMapping("/publications-list/{accountId}")
    public ResponseEntity<List<Map<String, Object>>> createPublicationList(@RequestBody List<Item> items, @PathVariable Integer accountId, @RequestParam Short idMargin){
        try {
            return new ResponseEntity<>(meliService.createPublicationList(items, accountId), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/publications-flow/{accountId}")
    public ResponseEntity<Boolean> createPublicationsFlow(@RequestBody List<ItemModel> items, @PathVariable Integer accountId, @RequestParam Short idMargin){
        try {
            return new ResponseEntity<>(meliService.createPublicationsFlow(items, accountId, idMargin), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/republish-product")
    public ResponseEntity<DetailsPublicationsMeliGrid> republishProduct(@RequestBody DetailsPublicationsMeliGrid product){
        try {
            return new ResponseEntity<>(meliService.updateProductPublished(product), HttpStatus.CREATED);
        }
        catch (ApiException e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/changeStatusPublication/{accountId}/{publicationId}")
    public ResponseEntity<Map<String, Object>> changeStatusPublication(@PathVariable Integer accountId, @PathVariable String publicationId, @RequestParam int status){
        return new ResponseEntity<>(meliService.changeStatusPublication(accountId, status, publicationId), HttpStatus.OK);
     }

    @PostMapping("/changeStatusMultiplePublications")
    public ResponseEntity<Map<String, Object>> changeStatusMultiplePublications(@RequestBody List<ChangeMultipleStatusRequest> multiples, @RequestParam int status){
        return new ResponseEntity<>(meliService.changeStatusMultiplePublications(multiples, status), HttpStatus.OK);
    }
}
