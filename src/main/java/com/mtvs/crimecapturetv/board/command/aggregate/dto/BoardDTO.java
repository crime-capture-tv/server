package com.mtvs.crimecapturetv.board.command.aggregate.dto;


import com.mtvs.crimecapturetv.board.command.aggregate.entity.Board;
import com.mtvs.crimecapturetv.board.command.aggregate.entity.enumType.BoardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class BoardDTO {

    private Long boardNo;           // 문의글 고유번호
    private String title;           // 문의사항 제목
    private String content;         // 문의사항 내용
    private BoardType boardType;    // 문의사항 카테고리
    private Long userNo;            // 회원명

    public static BoardDTO of(Board board) {
        return BoardDTO.builder()
                .boardNo(board.getBoardNo())
                .title(board.getTitle())
                .content(board.getContent())
                .boardType(board.getBoardType())
                .userNo(board.getUser().getNo())
                .build();
    }
}
