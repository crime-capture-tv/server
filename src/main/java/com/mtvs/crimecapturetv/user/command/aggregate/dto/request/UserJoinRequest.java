package com.mtvs.crimecapturetv.user.command.aggregate.dto.request;

import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import com.mtvs.crimecapturetv.user.command.aggregate.entity.enumtype.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {

    private String id;          // 아이디
    private String password;    // 비밀번호
    private String name;        // 이름
    private String email;       // 이메일
    private String phoneNumber; // 전화번호
    private String code;        // 이메일 인증 코드

    public User toEntity(String encodedPassword) {
        return User.builder()
                .id(this.id)
                .password(encodedPassword)
                .name(this.name)
                .email(this.email)
                .role(UserRole.USER)
                .phoneNumber(this.phoneNumber)
                .build();
    }

}
