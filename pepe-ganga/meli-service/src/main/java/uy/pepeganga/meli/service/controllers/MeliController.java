package uy.pepeganga.meli.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.pepeganga.business.common.entities.MeliAccount;
import uy.pepeganga.meli.service.services.IMeliService;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class MeliController {

    @Autowired
    IMeliService meliService;


    @PostMapping("/{profileId}")
    public ResponseEntity<MeliAccount> createAccountByProfile(@PathVariable Integer profileId, @RequestBody MeliAccount meliAccount) {
        return new ResponseEntity<>(meliService.createAccountByProfile(profileId, meliAccount), HttpStatus.CREATED);
    }


    @GetMapping("/by-profile/{profileId}")
    public ResponseEntity<List<MeliAccount>> meliAccountsByProfileId(@PathVariable Integer profileId) {
        return new ResponseEntity<>(meliService.meliAccountsByProfileId(profileId), HttpStatus.OK);
    }


    @GetMapping("/update/{accountId}")
    public ResponseEntity<MeliAccount> updateMeliAccount(@PathVariable Integer accountId, @RequestBody MeliAccount meliAccount) {
        return new ResponseEntity<>(meliService.updateMeliAccount(accountId, meliAccount), HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> updateMeliAccount(@PathVariable Integer accountId) {
        meliService.deleteMeliAccount(accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
