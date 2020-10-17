package uy.pepeganga.meli.service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import meli.ApiException;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.com.pepeganga.business.common.entities.MeliAccount;
import uy.pepeganga.meli.service.models.ApiMeliModelException;
import uy.pepeganga.meli.service.models.MeliResponseBodyException;
import uy.pepeganga.meli.service.services.IAuthService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    IAuthService authService;

    @Autowired
    ObjectMapper mapper;


    @GetMapping("/{profileId}/{code}")
    public ResponseEntity<Map<String, Object>> getToken(@PathVariable Integer profileId, @PathVariable String code ){

        Map<String, Object> map = new HashMap<>();
        try {
             map.put("authentication", authService.getToken(profileId, code));
             return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (ApiException e) {
            try {
                logger.error(" Error getting token Meli Response: {}", e.getResponseBody());
                map.put("error-meli", new ApiMeliModelException(e.getCode(), e.getResponseBody(),  mapper.readValue(e.getResponseBody(), MeliResponseBodyException.class)));
            } catch (JsonProcessingException ex) {
                logger.error(" Error parsing Meli Response", ex);
                map.put("error", ex.getMessage());
            }
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<MeliAccount> createAccountByProfile (){

        return null;
    }
}
