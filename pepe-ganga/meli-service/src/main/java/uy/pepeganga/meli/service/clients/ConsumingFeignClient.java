package uy.pepeganga.meli.service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uy.com.pepeganga.business.common.models.OrderDto;

import java.util.List;

@FeignClient(name = "consuming-store-service")
public interface ConsumingFeignClient {

    @PostMapping("/api/purchase/process")
    String processPurchases(@RequestBody List<OrderDto> ordersDto);
}
