package uy.com.pepeganga.consumingwsstore.controllers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.pepeganga.business.common.exceptions.PGException;
import uy.com.pepeganga.business.common.models.PurchaseNotification;
import uy.com.pepeganga.business.common.models.OrderDto;
import uy.com.pepeganga.business.common.models.ReasonResponse;
import uy.com.pepeganga.consumingwsstore.client.MeliFeignClient;
import uy.com.pepeganga.consumingwsstore.services.IPurchaseOrdersService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    IPurchaseOrdersService purchaseOrdersService;

    @Autowired
    MeliFeignClient meliFeignClient;

    @GetMapping("/process/{orderId}")
    public ResponseEntity<String> processPurchases(@PathVariable Long orderId) throws ParseException, PGException {
        if(orderId == null) {
            return new ResponseEntity<>("No se aceptan parámetros nulos", HttpStatus.BAD_REQUEST);
        }
        OrderDto orderDto = meliFeignClient.getRecentOrdersById(orderId);
        List<OrderDto> ordersList = new ArrayList<>();
        ordersList.add(orderDto);
        List<ReasonResponse> result = purchaseOrdersService.registerPurchaseOrders(ordersList);
        if (result.get(0).isSuccess())
            return new ResponseEntity<>(String.format("{status: Enviado, orderId: %s}", orderDto.getOrderId()), HttpStatus.ACCEPTED);
        else {
            JSONParser jsonParser = new JSONParser();
            JSONObject obj = (JSONObject) jsonParser.parse(result.get(0).getReason());
            throw new PGException("Error registrando orden en el sistema ERP", obj.get("message").toString(), HttpStatus.FAILED_DEPENDENCY.value() );
        }

    }

    @PostMapping("/notification")
    public ResponseEntity<String> processPurchasesNotification(@RequestBody PurchaseNotification purchaseNotification)  {
        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }
}
