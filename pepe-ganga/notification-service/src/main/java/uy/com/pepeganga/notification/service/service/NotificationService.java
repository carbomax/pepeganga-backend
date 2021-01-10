package uy.com.pepeganga.notification.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uy.com.pepeganga.business.common.entities.Notification;
import uy.com.pepeganga.notification.service.models.NotificationRequest;
import uy.com.pepeganga.notification.service.repository.NotificationRepository;

@Service
public class NotificationService implements uy.com.pepeganga.notification.service.service.INotificationService {

    private static final String TOPIC = "orders";

    @Autowired
    NotificationRepository repository;

    @Override
    public Notification saveNotification(NotificationRequest request) {
        if(request != null){
            Notification notification = new Notification();
            if(request.getTopic().contains(TOPIC)){
                notification.setApplicationId(request.getApplicationId());
                notification.setUserId(request.getUserId());
                notification.setAttempts(request.getAttempts());
                notification.setReceived(request.getReceived());
                notification.setSent(request.getSent());
                notification.setResource(request.getResource());
                notification.setTopic(request.getTopic());
                return repository.save(notification);
            } else return notification;
        } else throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);

    }
}
