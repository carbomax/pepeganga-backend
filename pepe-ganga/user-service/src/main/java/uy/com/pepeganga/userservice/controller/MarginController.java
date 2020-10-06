package uy.com.pepeganga.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.pepeganga.business.common.entities.Margin;
import uy.com.pepeganga.userservice.service.MarginService;

import java.util.List;

@RestController
@RequestMapping("/api/margins")
public class MarginController {

    @Autowired
    private MarginService marginService;

    @GetMapping("/{profileId}")
    public ResponseEntity<List<Margin>> getMargins(@PathVariable Integer profileId){
        return new ResponseEntity<>(marginService.getMargins(profileId), HttpStatus.OK);
    }


    @PutMapping("/{idProfile}/{idMargin}")
    public ResponseEntity<Margin> updateMarketplace(@RequestBody Margin margin, @PathVariable Integer idProfile, @PathVariable Short idMargin){
        return new ResponseEntity<>(marginService.updateMargin(margin, idProfile, idMargin), HttpStatus.OK);
    }

    @PostMapping("/{profileId}")
    public ResponseEntity<Margin> createMargin(@RequestBody Margin margin, @PathVariable Integer profileId){
        return new ResponseEntity<>(marginService.createMargin(margin, profileId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarketplace( @PathVariable Short id){
        marginService.deleteMargin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
