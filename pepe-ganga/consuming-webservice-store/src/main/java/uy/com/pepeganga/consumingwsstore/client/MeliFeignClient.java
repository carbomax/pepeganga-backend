package uy.com.pepeganga.consumingwsstore.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.pepeganga.business.common.models.OrderDto;
import uy.com.pepeganga.consumingwsstore.models.Pair;

import java.util.List;

@FeignClient(name = "meli-service")
public interface MeliFeignClient {

    @PostMapping("/api/accounts/update-stock")
    void updateStock(@RequestBody List<Pair> pairs, @RequestParam Long idData);

    @GetMapping("/api/orders/recent")
    List<OrderDto> getRecentOrdersByBatch(@RequestParam(defaultValue = "5", required = false) int quantity);

    @GetMapping("/api/orders/by-id/{id}")
    OrderDto getRecentOrdersById(@PathVariable Long id);
}