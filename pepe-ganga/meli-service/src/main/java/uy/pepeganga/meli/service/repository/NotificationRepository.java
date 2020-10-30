package uy.pepeganga.meli.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Transactional(readOnly = true)
    List<Notification> findByTopic(String topic);
}
