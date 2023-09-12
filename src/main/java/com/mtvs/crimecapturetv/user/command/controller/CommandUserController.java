package com.mtvs.crimecapturetv.user.command.controller;

import com.mtvs.crimecapturetv.exception.Response;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.UserDto;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.request.CommandUserJoinRequest;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.response.CommandUserJoinResponse;
import com.mtvs.crimecapturetv.user.command.service.CommandUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class CommandUserController {

    private final CommandUserService userService;

    @PostMapping("/join")
    public ResponseEntity<Response<CommandUserJoinResponse>> create(@RequestBody CommandUserJoinRequest request) {

        UserDto userDto = userService.join(request);

        return ResponseEntity.ok(Response.success(CommandUserJoinResponse.of(userDto)));

    }

}
