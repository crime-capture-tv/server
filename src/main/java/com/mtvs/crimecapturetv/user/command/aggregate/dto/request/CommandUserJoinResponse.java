package com.mtvs.crimecapturetv.user.command.aggregate.dto.request;

import com.mtvs.crimecapturetv.user.command.aggregate.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandUserJoinResponse {
    private String id;
    private String email;
    private String name;
    private String role;

    public static CommandUserJoinResponse of(UserDto userDto) {
        return CommandUserJoinResponse.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .role(userDto.getRole())
                .build();
    }
}
