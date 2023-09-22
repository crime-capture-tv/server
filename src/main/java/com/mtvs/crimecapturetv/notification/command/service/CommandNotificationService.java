package com.mtvs.crimecapturetv.notification.command.service;


import com.mtvs.crimecapturetv.notification.command.aggregate.dto.response.CommandNotificationResponse;
import com.mtvs.crimecapturetv.notification.command.aggregate.entity.Notification;
import com.mtvs.crimecapturetv.notification.command.aggregate.entity.enumtype.NotificationType;
import com.mtvs.crimecapturetv.notification.command.repository.CommandNotificationRepository;
import com.mtvs.crimecapturetv.exception.AppException;
import com.mtvs.crimecapturetv.exception.ErrorCode;
import com.mtvs.crimecapturetv.notification.command.repository.EmitterRepository;
import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import com.mtvs.crimecapturetv.user.command.repository.CommandUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.yaml.snakeyaml.emitter.Emitter;

import java.io.IOException;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class CommandNotificationService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final EmitterRepository emitterRepository;
    private final CommandNotificationRepository notificationRepository;
    private final CommandUserRepository userRepository;

    public Page<CommandNotificationResponse> getNotificationList(Long userNo, Pageable pageable) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));

        Page<CommandNotificationResponse> notifications = notificationRepository
                .findAllByUser(user, pageable).map(notification -> CommandNotificationResponse.of(notification));

        return notifications;

    }

    @Transactional
    public void notificationCheckAndDelete(Long notificationNo, Long userNo) {

        Notification notification = notificationRepository.findById(notificationNo)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUNDED));

        User user = userRepository.findByNo(userNo)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));

        if (notification.getUser().getNo() == user.getNo())
        {
            notificationRepository.delete(notification);
        }
    }

    public SseEmitter subscribe(Long userNo) {
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        try {
            sseEmitter.send(SseEmitter.event().name("connect"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        emitterRepository.save(userNo, sseEmitter);
        sseEmitter.onCompletion(() -> emitterRepository.deleteById(userNo));
        sseEmitter.onTimeout(() -> emitterRepository.deleteById(userNo));
        sseEmitter.onError((e) -> emitterRepository.deleteById(userNo));

        return sseEmitter;
    }

    private void sendNotification(SseEmitter sseEmitter, Long emitterId, CommandNotificationResponse notificationResponse) {
        try {
            sseEmitter.send(SseEmitter.event().name("Notification")
                    .data(notificationResponse, MediaType.APPLICATION_JSON));
        } catch (IOException e) {
            emitterRepository.deleteById(emitterId);
        }
    }

    public void send(User receiver, NotificationType notificationType, String url, String title) {
        System.out.println("알람 전송(저장) 요청이 들어왔습니다.");
        Notification notification = Notification.builder()
                .user(receiver)
                .notificationType(notificationType)
                .targetUrl(url)
                .notificationTitle(title)
                .build();

        System.out.println("알람 저장 시작");
        Notification savedNotification = notificationRepository.save(notification);
        System.out.println("알람 저장 완료");

        SseEmitter sseEmitter = emitterRepository.findEmitterByUserNo(receiver.getNo());
        if (sseEmitter != null) {
            sendNotification(sseEmitter, receiver.getNo(), CommandNotificationResponse.of(savedNotification));
        }
    }
}
