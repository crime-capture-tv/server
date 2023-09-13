package com.mtvs.crimecapturetv.user.command.controller;

import com.mtvs.crimecapturetv.user.command.aggregate.dto.request.CommandUserLoginRequest;
import com.mtvs.crimecapturetv.user.command.service.CommandUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
// spring security 로그인 test controller(프론트)
public class SecurityLoginController {

    private final CommandUserService userService;

    @GetMapping("/TestLogin")
    public String testLoginView() {
        return "TestLogin"; // 뷰 이름
    }

    @GetMapping("/username")
    public String currentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return "로그인되지 않았습니다.";
    }


}
