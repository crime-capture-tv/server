package com.mtvs.crimecapturetv.user.command.controller;

import com.mtvs.crimecapturetv.exception.Response;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.UserDto;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.request.UserJoinRequest;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.response.UserJoinResponse;
import com.mtvs.crimecapturetv.user.command.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Response<UserJoinResponse>> create(@RequestBody UserJoinRequest request) {

        UserDto userDto = userService.join(request);

        return ResponseEntity.ok(Response.success(UserJoinResponse.of(userDto)));

    }

}
