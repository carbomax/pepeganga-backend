package uy.com.pepeganga.consumingwsstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.com.pepeganga.consumingwsstore.configurations.ConsumingInitAsync;


@RestController
@RequestMapping("/api")
public class ClientSyncController {

    @Autowired
    ConsumingInitAsync initAsync;

    @GetMapping("/sync/update_stock")
    public ResponseEntity<String> updateStock() {
        initAsync.run();
        return new ResponseEntity<>("Starting Synchronization", HttpStatus.OK);
    }


}
