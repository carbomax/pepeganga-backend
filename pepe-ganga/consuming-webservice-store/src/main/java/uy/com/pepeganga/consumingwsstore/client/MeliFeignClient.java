package uy.com.pepeganga.consumingwsstore.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import uy.com.pepeganga.business.common.models.OrderDto;
import uy.com.pepeganga.consumingwsstore.models.Pair;

import java.util.List;

@FeignClient(name = "meli-service")
public interface MeliFeignClient {

    @PostMapping("/api/accounts/update-stock")
    void updateStock(@RequestBody List<Pair> pairs, @RequestParam Long idData);

    @GetMapping("/api/orders/recent")
    List<OrderDto> getRecentOrdersByBatch(@RequestParam(defaultValue = "5", required = false) int quantity);
}