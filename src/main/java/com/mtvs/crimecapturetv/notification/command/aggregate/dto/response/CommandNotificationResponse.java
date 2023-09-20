package com.mtvs.crimecapturetv.notification.command.aggregate.dto.response;


import com.mtvs.crimecapturetv.notification.command.aggregate.entity.Notification;
import com.mtvs.crimecapturetv.notification.command.aggregate.entity.enumtype.NotificationType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CommandNotificationResponse {

    private Long notificationNo;
    private String targetUrl;
    private String notificationTitle;
    private NotificationType notificationType;
    private Long no;

    public static CommandNotificationResponse of(Notification notification) {

        return new CommandNotificationResponse(notification.getNotificationNo(),
                notification.getTargetUrl(),
                notification.getNotificationTitle(),
                notification.getNotificationType(),
                notification.getUser().getNo());

    }
}
