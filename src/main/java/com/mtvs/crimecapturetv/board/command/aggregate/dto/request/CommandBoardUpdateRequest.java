package com.mtvs.crimecapturetv.board.command.aggregate.dto.request;

import com.mtvs.crimecapturetv.board.command.aggregate.entity.Board;
import com.mtvs.crimecapturetv.board.command.aggregate.entity.enumType.BoardType;
import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandBoardUpdateRequest {

    private String title;
    private String content;
    private BoardType boardType;

    public Board toEntity(User user) {
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .user(user)
                .build();
    }
}
