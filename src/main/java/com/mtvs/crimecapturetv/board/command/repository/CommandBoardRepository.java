package com.mtvs.crimecapturetv.board.command.repository;

import com.mtvs.crimecapturetv.board.command.aggregate.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CommandBoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByBoardNo(Long boardNo);

    void deleteByBoardNo(Long boardNo);
}
