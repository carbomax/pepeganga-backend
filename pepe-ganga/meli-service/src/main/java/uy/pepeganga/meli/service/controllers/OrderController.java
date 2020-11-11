package uy.pepeganga.meli.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<MeliOrders>> getAllOrdersByProfile(@PathVariable Integer profileId, @RequestParam List<String> statusFilter,
                                                                  @RequestParam(defaultValue = "") String nameClient,
                                                                  @RequestParam(defaultValue = "0") Long dateFrom,  @RequestParam(defaultValue = "99999999") Long dateTo,
                                                                  @RequestParam int page, @RequestParam int size){
        return new ResponseEntity<>(orderService.getAllOrdersByProfile(profileId, statusFilter, nameClient, dateFrom, dateTo, page, size), HttpStatus.OK);
    }

    @PutMapping("/update-carrier/{orderId}/{carrierId}")
    public ResponseEntity<Boolean> updateCarrier(@PathVariable Long orderId, @PathVariable int carrierId){
            return new ResponseEntity<>(orderService.updateCarrier(orderId, carrierId), HttpStatus.OK);
    }


    @PutMapping("/update-description/{orderId}")
    public ResponseEntity<Boolean> updateDescription(@PathVariable Long orderId, @RequestParam String description){
        return new ResponseEntity<>(orderService.updateDescription(orderId, description), HttpStatus.OK);
    }

    @PutMapping("/update-observation/{orderId}")
    public ResponseEntity<Boolean> updateObservation(@PathVariable Long orderId, @RequestParam String observation){
        return new ResponseEntity<>(orderService.updateObservation(orderId, observation), HttpStatus.OK);
    }

    @PutMapping("/update-invoice/{orderId}")
    public ResponseEntity<Boolean> updateInvoice(@PathVariable Long orderId, @RequestParam Long invoice){
        return new ResponseEntity<>(orderService.updateInvoice(orderId, invoice), HttpStatus.OK);
    }
}
