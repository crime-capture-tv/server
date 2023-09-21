package com.mtvs.crimecapturetv.notification.command.controller;

import com.mtvs.crimecapturetv.configuration.login.UserDetail;
import com.mtvs.crimecapturetv.exception.Response;
import com.mtvs.crimecapturetv.notification.command.aggregate.dto.response.CommandNotificationResponse;
import com.mtvs.crimecapturetv.notification.command.service.CommandNotificationService;
import io.lettuce.core.GeoArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class CommandNotificationController {

    private final CommandNotificationService notificationService;

    @GetMapping(value = "/sub",  produces = "text/event-stream")
    public SseEmitter subscribe(@AuthenticationPrincipal UserDetail userDetail) {
        if (userDetail == null) {
            return null;
        } else {
            return notificationService.subscribe(userDetail.getNo());
        }
    }

    @GetMapping("/check/{notificationNo}")
    public void notificationCheck(@PathVariable Long notificationNo, @AuthenticationPrincipal UserDetail userDetail) {
        notificationService.notificationCheckAndDelete(notificationNo, userDetail.getNo());
    }

    @GetMapping("/list")
    public ResponseEntity<Response<List<CommandNotificationResponse>>> getNotificationList(@AuthenticationPrincipal UserDetail userDetail, @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable) {
        if (userDetail != null) {
            Page<CommandNotificationResponse> notificationResponses = notificationService.getNotificationList(userDetail.getNo(), pageable);
            return ResponseEntity.ok(Response.success(notificationResponses.getContent()));
        }
        return ResponseEntity.ok(Response.success(null));
    }

}
