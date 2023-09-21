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
    private String notificationMessage;
    private String notificationTitle;
    private String notificationType;

    public static CommandNotificationResponse of(Notification notification) {
        String notificationTitle = notification.getNotificationTitle();
        if (notificationTitle.length() > 9) {
            notificationTitle = notificationTitle.substring(0,8);
            notificationTitle = "["+notificationTitle+"...]";
        }else {
            notificationTitle = "["+notificationTitle+"]";
        }

        System.out.println(notificationTitle);

        return new CommandNotificationResponse(notification.getNotificationNo(),
                notification.getTargetUrl(),
                notification.getNotificationType().getMessage(),
                notificationTitle,
                notification.getNotificationType().toString());


    }
}
