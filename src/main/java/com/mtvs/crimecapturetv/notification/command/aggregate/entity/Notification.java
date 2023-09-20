package com.mtvs.crimecapturetv.notification.command.aggregate.entity;

import com.mtvs.crimecapturetv.notification.command.aggregate.entity.enumtype.NotificationType;
import com.mtvs.crimecapturetv.global.common.entity.BaseEntity;
import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Notification_TB")
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_no")
    private Long notificationNo;

    @Column(name = "target_url")
    private String targetUrl;

    @Column(name = "notification_title")
    private String notificationTitle;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

}
