package com.mtvs.crimecapturetv.user.command.aggregate.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommandFindPasswordRequest {
    private String id;
    private String email;
}
