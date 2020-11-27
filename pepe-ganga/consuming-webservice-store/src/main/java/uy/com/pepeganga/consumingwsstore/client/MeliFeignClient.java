package uy.com.pepeganga.consumingwsstore.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "meli-service")
public interface MeliFeignClient {

    @GetMapping("/api/accounts/updateStock")
    boolean updateStock(@RequestParam Integer stock, @RequestParam String sku);
}