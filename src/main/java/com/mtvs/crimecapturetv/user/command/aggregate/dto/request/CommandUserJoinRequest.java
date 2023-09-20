package com.mtvs.crimecapturetv.user.command.aggregate.dto.request;

import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import com.mtvs.crimecapturetv.user.command.aggregate.entity.enumtype.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandUserJoinRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String id;          // 아이디

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;    // 비밀번호

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;        // 이름

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
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
