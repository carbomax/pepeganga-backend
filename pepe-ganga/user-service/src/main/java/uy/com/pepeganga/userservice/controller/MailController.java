package uy.com.pepeganga.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import uy.com.pepeganga.userservice.models.EmailBody;
import uy.com.pepeganga.userservice.service.mail.EmailService;

import java.util.Map;

@RestController
@RequestMapping("api")
public class MailController {

    @Autowired
    EmailService emailService;

    @PostMapping("send")
    public String sendEmail(@RequestBody EmailBody body){
        try {
            emailService.sendSimpleMessage(body.getTo(), body.getSubject(), body.getText());
            return "OK";
        }catch (MailException e){
            System.out.println(e.getMessage());
        }
        return "NOT";
    }

    @PostMapping("send/attach")
    public String sendEmailWithAttachment(@RequestBody EmailBody body){
        try {
            emailService.sendMessageWithAttachment(body.getTo(), body.getSubject(), body.getText(), body.getAttach());
            return "OK";
        }catch (MailException e){
            System.out.println(e.getMessage());
        }
        return "NOT";
    }



}
