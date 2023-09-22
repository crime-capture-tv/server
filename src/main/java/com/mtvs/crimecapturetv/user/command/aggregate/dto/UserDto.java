package com.mtvs.crimecapturetv.user.command.aggregate.dto;

import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import com.mtvs.crimecapturetv.user.command.aggregate.entity.enumtype.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long no;
    private String name;
    private String id;
    private String password;
    private String email;
    private String phoneNumber;
    private String role;


    public static UserDto of(User user){
        return UserDto.builder()
                .no(user.getNo())
                .name(user.getName())
                .id(user.getId())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().name())
                .build();
    }

}
