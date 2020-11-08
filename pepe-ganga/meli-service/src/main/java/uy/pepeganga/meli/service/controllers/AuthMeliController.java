package uy.pepeganga.meli.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.pepeganga.meli.service.services.IApiService;
import uy.pepeganga.meli.service.services.IMeliService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthMeliController {

    @Autowired
    IApiService apiService;

    @Autowired
    IMeliService meliService;


    @GetMapping("/{accountId}/{code}")
    public ResponseEntity<Map<String, Object>> authorizeAccount(@PathVariable Integer accountId, @PathVariable String code ){
      return apiService.updateAfterToken(accountId, code);
    }

    @GetMapping("/synchronize-account/{accountId}")
    public ResponseEntity<Map<String, Object>> synchronizeAccount(@PathVariable Integer accountId){
        return new ResponseEntity<>( apiService.synchronizeAccount(accountId), HttpStatus.OK);
    }

}
