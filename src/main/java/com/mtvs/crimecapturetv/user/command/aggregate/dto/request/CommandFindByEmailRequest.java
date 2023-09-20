package com.mtvs.crimecapturetv.user.command.aggregate.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandFindByEmailRequest {
    private String id;
    private String email;

}
