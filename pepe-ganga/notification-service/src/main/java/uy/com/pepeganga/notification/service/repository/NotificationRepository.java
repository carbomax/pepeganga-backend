package uy.com.pepeganga.notification.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.com.pepeganga.business.common.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
