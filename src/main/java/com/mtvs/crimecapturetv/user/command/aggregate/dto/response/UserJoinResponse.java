package com.mtvs.crimecapturetv.user.command.aggregate.dto.response;

import com.mtvs.crimecapturetv.user.command.aggregate.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinResponse {

    private String id;
    private String email;
    private String name;
    private String phoneNumber;
    private String role;

    public static UserJoinResponse of (UserDto userDto){
        return UserJoinResponse.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .phoneNumber(userDto.getPhoneNumber())
                .role(userDto.getRole())
                .build();
    }
}
