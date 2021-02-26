package uy.com.pepeganga.consumingwsstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.com.pepeganga.business.common.models.PurchaseNotification;
import uy.com.pepeganga.business.common.models.OrderDto;
import uy.com.pepeganga.consumingwsstore.services.IPurchaseOrdersService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    IPurchaseOrdersService purchaseOrdersService;

    @PostMapping("/process")
    public ResponseEntity<String> processPurchases(@RequestBody OrderDto ordersDto)  {
        List<OrderDto> ordersList = new ArrayList<>();
        ordersList.add(ordersDto);
        purchaseOrdersService.registerPurchaseOrders(ordersList);

        return new ResponseEntity<>(String.format("Proccesing %s", ordersDto.getOrderId()), HttpStatus.ACCEPTED);
    }

    @PostMapping("/notification")
    public ResponseEntity<String> processPurchasesNotification(@RequestBody PurchaseNotification purchaseNotification)  {
        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }
}
