package com.mtvs.crimecapturetv.board.command.controller;

import com.mtvs.crimecapturetv.board.command.aggregate.dto.BoardDTO;
import com.mtvs.crimecapturetv.board.command.aggregate.dto.request.CommandBoardCreateRequest;
import com.mtvs.crimecapturetv.board.command.aggregate.dto.request.CommandBoardDeleteRequest;
import com.mtvs.crimecapturetv.board.command.aggregate.dto.request.CommandBoardUpdateRequest;
import com.mtvs.crimecapturetv.board.command.aggregate.dto.response.CommandBoardCreateResponse;
import com.mtvs.crimecapturetv.board.command.aggregate.dto.response.CommandBoardDeleteResponse;
import com.mtvs.crimecapturetv.board.command.aggregate.dto.response.CommandBoardUpdateResponse;
import com.mtvs.crimecapturetv.board.command.service.CommandBoardService;
import com.mtvs.crimecapturetv.exception.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class CommandBoardController {

    private final CommandBoardService commandBoardService;

    @PostMapping("/write-board")
    public ResponseEntity<Response<CommandBoardCreateResponse>> create(@RequestBody CommandBoardCreateRequest request, Long id) {

        BoardDTO boardDTO = commandBoardService.writeBoard(request, id);

        return ResponseEntity.ok(Response.success(CommandBoardCreateResponse.of(boardDTO)));

    }

    @DeleteMapping("/{boardNo}")
    public ResponseEntity<Response<CommandBoardDeleteResponse>> delete(@RequestBody CommandBoardDeleteRequest request, Long id, @PathVariable Long boardNo) {

        Long deletedBoard = commandBoardService.deleteBoard(request, id, boardNo);

        return ResponseEntity.ok(Response.success(new CommandBoardDeleteResponse("삭제 완료", deletedBoard )));
    }

    @PutMapping("/{boardNo}")
    public ResponseEntity<Response<CommandBoardUpdateResponse>> modify(@RequestBody CommandBoardUpdateRequest request, Long id, @PathVariable Long boardNo) {

        Long modifiedBoard = commandBoardService.modifyBoard(request, id, boardNo);

        return ResponseEntity.ok(Response.success(new CommandBoardUpdateResponse("수정 완료", modifiedBoard)));
    }

    @GetMapping("/{boardNo}")
    public ResponseEntity<Response<BoardDTO>> detail(@PathVariable Long boardNo, Long id) {

        BoardDTO boardDTO = commandBoardService.detail(id, boardNo);
        return ResponseEntity.ok(Response.success(boardDTO));
    }


}
