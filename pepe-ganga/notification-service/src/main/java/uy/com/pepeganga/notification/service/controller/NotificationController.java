package uy.com.pepeganga.notification.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uy.com.pepeganga.notification.service.models.NotificationRequest;
import uy.com.pepeganga.notification.service.service.INotificationService;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.net.InetAddress.getLocalHost;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    INotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> receiveMeliNotification(@RequestBody NotificationRequest notification){
        notificationService.saveNotification(notification);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> test(){
        try {
            InetAddress address = getLocalHost();

            return new ResponseEntity<>(address.getCanonicalHostName(), HttpStatus.OK);
        } catch (UnknownHostException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Desconocido");
        }

    }
}
