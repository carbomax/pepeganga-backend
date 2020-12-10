package uy.com.pepeganga.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.pepeganga.auth.services.IUserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
public class ResetPasswordController {

    @Autowired
    IUserService userService;


    @PostMapping("send/email-reset-password")
    public ResponseEntity<Map<String, Object>> sendEmailToResetPassword(@RequestParam String email, @RequestParam String url){
        return new ResponseEntity<>(userService.sendEmailToResetPassword(email, url), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Boolean> isValidToken(@RequestParam String token){
        return new ResponseEntity<>(userService.isValidTokenToResetPassword(token), HttpStatus.ACCEPTED);
    }
}
