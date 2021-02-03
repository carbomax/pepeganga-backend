package uy.com.pepeganga.consumingwsstore.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.com.pepeganga.business.common.models.PurchaseNotification;
import uy.com.pepeganga.business.common.models.OrderDto;

import java.util.List;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @PostMapping("/process")
    public ResponseEntity<String> processPurchases(@RequestBody List<OrderDto> ordersDto)  {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(String.format("Proccesing %s", ordersDto.get(0).toString()), HttpStatus.ACCEPTED);
    }

    @PostMapping("/notification")
    public ResponseEntity<String> processPurchasesNotification(@RequestBody PurchaseNotification purchaseNotification)  {
        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }
}
