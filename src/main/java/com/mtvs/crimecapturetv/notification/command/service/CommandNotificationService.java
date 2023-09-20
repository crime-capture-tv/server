package com.mtvs.crimecapturetv.notification.command.service;


import com.mtvs.crimecapturetv.notification.command.aggregate.dto.response.CommandNotificationResponse;
import com.mtvs.crimecapturetv.notification.command.aggregate.entity.Notification;
import com.mtvs.crimecapturetv.notification.command.repository.CommandNotificationRepository;
import com.mtvs.crimecapturetv.exception.AppException;
import com.mtvs.crimecapturetv.exception.ErrorCode;
import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import com.mtvs.crimecapturetv.user.command.repository.CommandUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class CommandNotificationService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final CommandNotificationRepository commandNotificationRepository;
    private final CommandUserRepository commandUserRepository;

    public Page<CommandNotificationResponse> getNotificationList(Long userNo, Pageable pageable) {
        User user = commandUserRepository.findById(userNo)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));

        Page<CommandNotificationResponse> notifications = commandNotificationRepository.findAllByUser(user, pageable).map(notification -> CommandNotificationResponse.of(notification));

        return notifications;

    }

    @Transactional
    public void notificationCheckAndDelete(Long notificationNo, Long userNo) {

        Notification notification = commandNotificationRepository.findById(userNo)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUNDED));

        User user = commandUserRepository.findById(userNo)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));

    }





}
