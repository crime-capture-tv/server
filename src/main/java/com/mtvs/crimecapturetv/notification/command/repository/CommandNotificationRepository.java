package com.mtvs.crimecapturetv.notification.command.repository;

import com.mtvs.crimecapturetv.notification.command.aggregate.entity.Notification;
import com.mtvs.crimecapturetv.notification.command.aggregate.entity.enumtype.NotificationType;
import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CommandNotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findAllByUser(User user, Pageable pageable);


    Optional<Notification> findByUserAndNotificationType(User user, NotificationType type);
}
