package com.mtvs.crimecapturetv.board.command.aggregate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandBoardDeleteResponse {

    private String message;
    private Long boardNo;
}
