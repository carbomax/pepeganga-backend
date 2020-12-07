package uy.com.pepeganga.consumingwsstore.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import uy.com.pepeganga.business.common.entities.UpdatesOfSystem;
import uy.com.pepeganga.consumingwsstore.models.Pair;
import java.util.List;

@FeignClient(name = "meli-service")
public interface MeliFeignClient {

    @PostMapping("/api/accounts/update-stock")
    Boolean updateStock(@RequestBody List<Pair> pairs, @RequestParam Long idData);
}