package com.mtvs.crimecapturetv.user.command.aggregate.dto.response;

import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandMyPageInfoResponse {

    private String id;
    private String name;

    public static CommandMyPageInfoResponse of(User user) {
        return builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

}
