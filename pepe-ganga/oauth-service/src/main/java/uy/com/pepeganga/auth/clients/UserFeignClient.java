package uy.com.pepeganga.auth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uy.com.pepeganga.business.common.entities.User;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("/api/users/find-by-email")
    User findUserByEmail(@RequestParam String email);
}
