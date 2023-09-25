package com.mtvs.crimecapturetv.store.command.controller;


import com.mtvs.crimecapturetv.board.command.service.CommandBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boards")
public class exxController {

    private final CommandBoardService commandBoardService;

    public exxController(CommandBoardService commandBoardService) {
        this.commandBoardService = commandBoardService;
    }

    @GetMapping("/board-list")
    public String boardList() {
        return "boards/board-list";
    }

    @GetMapping("/board")
    public String board() {
        return "boards/board";
    }
}
