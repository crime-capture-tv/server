package com.mtvs.crimecapturetv.user.command.controller;

import com.mtvs.crimecapturetv.exception.Response;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.UserDto;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.request.CommandFindByEmailRequest;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.request.CommandUserJoinRequest;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.response.CommandUserJoinResponse;
import com.mtvs.crimecapturetv.user.command.service.CommandUserService;
import com.mtvs.crimecapturetv.user.command.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.web.servlet.headers.HeadersSecurityMarker;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class CommandUserController {

    private final CommandUserService userService;
    private final EmailService emailService;

    // 회원 가입
    @PostMapping("/join")
    public ResponseEntity<Response<CommandUserJoinResponse>> create(CommandUserJoinRequest request) {
        UserDto userDto = userService.join(request);
        return ResponseEntity.ok(Response.success(CommandUserJoinResponse.of(userDto)));
    }

    // 회원 가입 시 아이디 중복 체크
    @GetMapping("/check-id")
    public ResponseEntity<Response<Boolean>> checkId(@RequestParam String id) {
        return ResponseEntity.ok(Response.success(userService.checkId(id)));
    }

    // 회원 가입 시 이메일 중복 체크
    @GetMapping("/check-email")
    public ResponseEntity<Response<Boolean>> checkEmail(@RequestParam String email ) {
        return ResponseEntity.ok(Response.success(userService.checkEmail(email)));
    }

    // 인증 메일 보내기
    @GetMapping("/send-auth-email")
    public Response<String> sendAuthEmail(@RequestParam String email) throws Exception {
        System.out.println("email = " + email);
        return Response.success(emailService.sendLoginAuthMessage(email));
    }

    // 인증 메일 확인하기
    @GetMapping("/check-auth-email")
    public Response<Boolean> checkAuthEmail(@RequestParam String code) {
        System.out.println(code);
        if (emailService.getData(code) == null){
            log.info(code, "실패");
            return Response.success(false);
        }else {
            log.info(code, "성공");
            return Response.success(true);
        }
    }

    // 이메일로 아이디 찾기
    @GetMapping("/find-id-by-email")
    public ResponseEntity<Response<String>> findIdByEmail(CommandFindByEmailRequest request) throws Exception {
        return ResponseEntity.ok(Response.success(emailService.sendFoundIdMessage(request.getEmail())));
    }

    // 이메일로 비밀번호 재설정
    @GetMapping("/find-pw-by-email")
    public ResponseEntity<Response<String>> findPwByEmail(CommandFindByEmailRequest request) throws Exception {
        return ResponseEntity.ok(Response.success(emailService.sendFoundPasswordMessage(request.getEmail(), request.getId())));
    }

}
