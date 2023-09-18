package com.mtvs.crimecapturetv.board.command.aggregate.dto.response;

import com.mtvs.crimecapturetv.board.command.aggregate.dto.BoardDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandBoardCreateResponse {

    private String title;
    private String content;
    private Long userNo;

    public static CommandBoardCreateResponse of (BoardDTO boardDTO) {
        return CommandBoardCreateResponse.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .userNo(boardDTO.getUserNo())
                .build();
    }

}
