package uy.com.pepeganga.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.pepeganga.userservice.models.ResetPassword;
import uy.com.pepeganga.userservice.service.IUserService;
import uy.com.pepeganga.userservice.service.mail.EmailService;

import java.util.Map;


@RestController
@RequestMapping("reset")
public class ResetPasswordController {


    @Autowired
    IUserService userService;

    @Autowired
    EmailService emailService;

    @PostMapping("send/email-reset-password")
    public ResponseEntity<Map<String, Object>> sendEmailToResetPassword(@RequestParam String email, @RequestParam String url){
        return new ResponseEntity<>(emailService.sendEmailToResetPassword(email, url), HttpStatus.OK);
    }

    @GetMapping("valid-token-by-reset-password")
    public ResponseEntity<Boolean> isValidToken(@RequestParam String token){
        return new ResponseEntity<>(userService.isValidTokenToResetPassword(token), HttpStatus.ACCEPTED);
    }

    @GetMapping("user-enabled-by-token")
    public ResponseEntity<Boolean> isUserEnabledByToken(@RequestParam String token){
        return new ResponseEntity<>(userService.isUserEnabledByToken(token), HttpStatus.OK);
    }

    @PostMapping("change-password")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody ResetPassword resetPassword) {
        return new ResponseEntity<>(userService.changePassword(resetPassword), HttpStatus.ACCEPTED);
    }
}
