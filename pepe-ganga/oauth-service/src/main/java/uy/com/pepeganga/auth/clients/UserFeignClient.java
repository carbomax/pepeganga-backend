package uy.com.pepeganga.auth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.pepeganga.business.common.entities.User;
import uy.com.pepeganga.business.common.models.AuthAddInformationClaim;

import java.util.Map;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("/api/users/find-by-email")
    User findUserByEmail(@RequestParam String email);

    @GetMapping("/api/profiles/by-user-email/{email}")
    AuthAddInformationClaim findProfileByUserEmail(@PathVariable String email);

    @PutMapping("/api/users/update-user/{id}")
    User updateUser(@RequestBody User user, @PathVariable Integer id);
}
