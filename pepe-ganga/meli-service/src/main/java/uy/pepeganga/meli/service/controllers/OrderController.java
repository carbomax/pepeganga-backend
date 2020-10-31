package uy.pepeganga.meli.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.pepeganga.business.common.entities.MeliOrders;
import uy.pepeganga.meli.service.services.IOrderService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    IOrderService orderService;

    @GetMapping("/{accountId}")
    public ResponseEntity<Map<String, Object>> getOrdersByAccount(@PathVariable Integer accountId) {
        return new ResponseEntity<>(orderService.getOrdersByAccount(accountId), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test(){
        orderService.schedulingOrdersV2();
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("deleteTest")
    public ResponseEntity<Object> test2(){
        return new ResponseEntity<>(orderService.delete(), HttpStatus.OK);
    }


    @GetMapping("/by-all-profile-accounts/{profileId}")
    public ResponseEntity<List<MeliOrders>> getAllOrdersByProfile(@PathVariable Integer profileId, @RequestParam int page, @RequestParam int size){
        return new ResponseEntity<>(orderService.getAllOrdersByProfile(profileId, page, size), HttpStatus.OK);
    }

}
