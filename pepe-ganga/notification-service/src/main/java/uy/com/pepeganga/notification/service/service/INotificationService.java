package uy.com.pepeganga.notification.service.service;


import uy.com.pepeganga.business.common.entities.Notification;
import uy.com.pepeganga.notification.service.models.NotificationRequest;

public interface INotificationService {

   Notification saveNotification(NotificationRequest notification);
}
