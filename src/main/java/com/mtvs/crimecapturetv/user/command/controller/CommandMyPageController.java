package com.mtvs.crimecapturetv.user.command.controller;

import com.mtvs.crimecapturetv.configuration.login.UserDetail;
import com.mtvs.crimecapturetv.exception.Response;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.response.CommandMyPageInfoResponse;
import com.mtvs.crimecapturetv.user.command.service.CommandMyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class CommandMyPageController {

    private final CommandMyPageService mypageService;

    @GetMapping("/")
    public ResponseEntity<Response<CommandMyPageInfoResponse>> myPageInfo(@AuthenticationPrincipal UserDetail userDetail) {

        CommandMyPageInfoResponse myPageInfoResponse = mypageService.getMyPageInfo(userDetail.getId());

        return ResponseEntity.ok(Response.success(myPageInfoResponse));
    }

}
