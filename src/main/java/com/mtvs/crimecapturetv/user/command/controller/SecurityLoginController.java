package com.mtvs.crimecapturetv.user.command.controller;

import com.mtvs.crimecapturetv.user.command.aggregate.dto.UserDto;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.request.CommandUserJoinRequest;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.request.CommandUserLoginRequest;
import com.mtvs.crimecapturetv.user.command.service.CommandUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
// spring security 로그인 test controller(프론트)
public class SecurityLoginController {

    private final CommandUserService userService;

    // 회원가입 페이지
//    @GetMapping("/join")
//    public String joinPage(Model model) {
//        model.addAttribute("userJoinRequest", new CommandUserJoinRequest());
//        return "users/join";
//    }

    // 로그인 성공 페이지
    @GetMapping("/login-success")
    public ResponseEntity<UserDto> login(Principal principal) {
        UserDto userDto = userService.findUser(principal.getName());
        return ResponseEntity.ok().body(userDto);
    }

    // 로그인 실패 페이지
    @PostMapping("/login-fail")
    public ResponseEntity<Object> login2(HttpServletRequest request) {
        return ResponseEntity.ok().body(request.getAttribute("LoginFailMessage"));
    }

    // 로그인 페이지
//    @GetMapping("/login")
//    public String loginPage(Model model) {
//        model.addAttribute("userLoginRequest", new CommandUserLoginRequest());
//
//        return "users/login";
//    }

}
