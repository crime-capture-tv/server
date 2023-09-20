package com.mtvs.crimecapturetv.board.command.service;

import com.mtvs.crimecapturetv.board.command.aggregate.dto.BoardDTO;
import com.mtvs.crimecapturetv.board.command.aggregate.dto.request.CommandBoardCreateRequest;
import com.mtvs.crimecapturetv.board.command.aggregate.dto.request.CommandBoardDeleteRequest;
import com.mtvs.crimecapturetv.board.command.aggregate.dto.request.CommandBoardUpdateRequest;
import com.mtvs.crimecapturetv.board.command.aggregate.entity.Board;
import com.mtvs.crimecapturetv.board.command.repository.CommandBoardRepository;
import com.mtvs.crimecapturetv.exception.AppException;
import com.mtvs.crimecapturetv.exception.ErrorCode;
import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import com.mtvs.crimecapturetv.user.command.repository.CommandUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommandBoardService {

    private final CommandBoardRepository commandBoardRepository;
    private final CommandUserRepository commandUserRepository;

    // 게시물 저장
    @Transactional
    public BoardDTO writeBoard(CommandBoardCreateRequest commandBoardCreateRequest, Long id) {

        User user = commandUserRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));

        Board board = commandBoardRepository.save(commandBoardCreateRequest.toEntity(user));
        return BoardDTO.of(board);
    }

    // 게시물 삭제
    @Transactional
    public Long deleteBoard(CommandBoardDeleteRequest commandBoardDeleteRequest, Long id, Long boardNo) {

        User user = commandUserRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));

        Board board = commandBoardRepository.findByBoardNo(boardNo)
                .orElseThrow(() -> new AppException(ErrorCode.BOARD_NOT_FOUNDED));

        commandBoardRepository.deleteByBoardNo(boardNo);
        return boardNo;

    }

    // 게시물 수정
    @Transactional
    public Long modifyBoard(CommandBoardUpdateRequest commandBoardUpdateRequest, Long id, Long boardNo) {

        User user = commandUserRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));

        Board board = commandBoardRepository.findByBoardNo(boardNo)
                .orElseThrow(() -> new AppException(ErrorCode.BOARD_NOT_FOUNDED));

        board.modifyBoard(commandBoardUpdateRequest.getTitle(), commandBoardUpdateRequest.getContent(), commandBoardUpdateRequest.getBoardType());

        commandBoardRepository.save(board);
        return boardNo;
    }

    // 게시물 조회
    public BoardDTO detail(Long id, Long boardNo) {

        User user = commandUserRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));

        Board board = commandBoardRepository.findByBoardNo(boardNo)
                .orElseThrow(() -> new AppException(ErrorCode.BOARD_NOT_FOUNDED));

        return BoardDTO.of(board);
    }




}
