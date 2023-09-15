package com.mtvs.crimecapturetv.user.command.controller;

import com.mtvs.crimecapturetv.exception.Response;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.UserDto;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.request.CommandUserJoinRequest;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.response.CommandUserJoinResponse;
import com.mtvs.crimecapturetv.user.command.service.CommandUserService;
import com.mtvs.crimecapturetv.user.command.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class CommandUserController {

    private final CommandUserService userService;
    private final EmailService emailService;

    @PostMapping("/join")
    public ResponseEntity<Response<CommandUserJoinResponse>> create(@RequestBody CommandUserJoinRequest request) {

        UserDto userDto = userService.join(request);

        return ResponseEntity.ok(Response.success(CommandUserJoinResponse.of(userDto)));

    }

    // 인증 메일 보내기
    @GetMapping("/send-auth-email")
    public Response<String> sendAuthEmail(@RequestParam String email) throws Exception {
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

}
