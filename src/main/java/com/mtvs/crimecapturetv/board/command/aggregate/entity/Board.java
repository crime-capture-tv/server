package com.mtvs.crimecapturetv.board.command.aggregate.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.mtvs.crimecapturetv.board.command.aggregate.entity.enumType.BoardType;
import com.mtvs.crimecapturetv.global.common.entity.BaseEntity;
import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Board_TB")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Long boardNo;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;


    public void modifyBoard(String title, String content, BoardType boardType) {
        this.title = title;
        this.content = content;
        this.boardType = boardType;
    }
}
