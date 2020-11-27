package uy.com.pepeganga.consumingwsstore.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uy.com.pepeganga.business.common.entities.Item;

import java.util.List;

@FeignClient(name = "meli-service")
public interface MeliFeignClient {

    @PostMapping("/api/accounts/updateStock")
    boolean updateStock(@RequestBody List<Item> items);
}