package uy.com.pepeganga.consumingwsstore.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import uy.com.pepeganga.consumingwsstore.models.Pair;

import java.util.List;
import java.util.Map;

@FeignClient(name = "meli-service")
public interface MeliFeignClient {

    @PostMapping("/api/accounts/update-stock")
    Boolean updateStock(@RequestBody List<Pair> pairs);

    @PostMapping("/api/accounts/update-string")
    Boolean updateString(@RequestBody List<String> pairs);

}