package com.mtvs.crimecapturetv.notification.command.service;

import com.mtvs.crimecapturetv.exception.AppException;
import com.mtvs.crimecapturetv.exception.ErrorCode;
import com.mtvs.crimecapturetv.notification.command.aggregate.entity.Notification;
import com.mtvs.crimecapturetv.notification.command.aggregate.entity.enumtype.NotificationType;
import com.mtvs.crimecapturetv.notification.command.repository.CommandNotificationRepository;
import com.mtvs.crimecapturetv.notification.command.repository.EmitterRepository;
import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import com.mtvs.crimecapturetv.user.command.repository.CommandUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Optional;
import static  org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CommandNotificationServiceTest {

    private final EmitterRepository emitterRepository = mock(EmitterRepository.class);
    private final CommandNotificationRepository notificationRepository = mock(CommandNotificationRepository.class);
    private final CommandUserRepository userRepository = mock(CommandUserRepository.class);

    CommandNotificationService notificationService;

    @BeforeEach
    void beforeEach() {
        notificationService = new CommandNotificationService(emitterRepository, notificationRepository, userRepository);
    }

    @Test
    @DisplayName("알람 리스트 호출 실패 - 유저 정보 없음")
    void getNotificationList_fail() {

        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        when(userRepository.findByNo(1L)).thenReturn(Optional.empty());

        // then
        AppException error = assertThrows(AppException.class, () -> notificationService.getNotificationList(1L, pageable));
        assertThat(error.getErrorCode(), is(ErrorCode.USER_NOT_FOUNDED));
    }

    @Test
    @DisplayName("알람 확인 실패 - 알람 없음")
    void notificationCheckAndDelete_fail1() {

        // when
        when(notificationRepository.findById(1L)).thenReturn(Optional.empty());

        // then
        AppException error = assertThrows(AppException.class, () -> notificationService.notificationCheckAndDelete(1L, 1L));
        assertThat(error.getErrorCode(), is(ErrorCode.NOTIFICATION_NOT_FOUNDED));
    }

    @Test
    @DisplayName("알람 확인 실패 - 유저 정보 없음")
    void notificationCheckAndDelete_fail2() {

        // given
        Notification notification = spy(Notification.class);

        // when
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));
        when(userRepository.findByNo(1L)).thenReturn(Optional.empty());

        // then
        AppException error = assertThrows(AppException.class, () -> notificationService.notificationCheckAndDelete(1L, 1L));
        assertThat(error.getErrorCode(), is(ErrorCode.USER_NOT_FOUNDED));
    }

    @Test
    @DisplayName("알람 확인 실패 - 유저 정보 다름")
    void notificationCheckAndDelete_fail3() {

        // given
        User user = spy(User.builder().No(2L).build());
        Notification notification = spy(Notification.builder().user(user).build());

        // when
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));
        when(userRepository.findByNo(1L)).thenReturn(Optional.of(user));

        // then
        AppException error = assertThrows(AppException.class, () -> notificationService.notificationCheckAndDelete(1L, 2L));
        assertThat(error.getErrorCode(), is(ErrorCode.USER_NOT_FOUNDED));
    }

    @Test
    @DisplayName("알람 확인 성공")
    void notificationCheckAndDelete_success() {

        // given
        User user = spy(User.builder().No(1L).build());
        Notification notification = spy(Notification.builder().user(user).build());

        // when
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));
        when(userRepository.findByNo(1L)).thenReturn(Optional.of(user));

        // then
        notificationService.notificationCheckAndDelete(1L, 1L);

    }

    @Test
    @DisplayName("Sse 구독 성공")
    void subscribe_success() {
        SseEmitter sseEmitter = notificationService.subscribe(1L);
        assertThat(60L*1000*60, is(sseEmitter.getTimeout()));
    }

    @Test
    @DisplayName("알람 보내기 실패 - emitter 없음")
    void send_fail() {

        // given
        User receiver = spy(User.builder().No(1L).build());

        // when
        when(emitterRepository.findEmitterByUserNo(1L)).thenReturn(null);

        // then
        notificationService.send(receiver, NotificationType.CRIME_VIDEO_NOTIFICATION, "testUrl", "testTitle");
    }

    @Test
    @DisplayName("알람 보내기 성공")
    void send_success() {

        // given
        User receiver = spy(User.builder().No(1L).build());
        SseEmitter sseEmitter = spy(SseEmitter.class);
        Notification savedNotification = spy(Notification.builder().notificationNo(1L).targetUrl("testUrl").notificationType(NotificationType.CRIME_VIDEO_NOTIFICATION).notificationTitle("testTitle").build());

        // when
        when(emitterRepository.findEmitterByUserNo(1L)).thenReturn(sseEmitter);
        when(notificationRepository.save(any())).thenReturn(savedNotification);

        // then
        notificationService.send(receiver, NotificationType.CRIME_VIDEO_NOTIFICATION, "testUrl", "testTitle");

    }
}