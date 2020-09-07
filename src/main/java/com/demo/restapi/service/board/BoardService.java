package com.demo.restapi.service.board;

import com.demo.restapi.common.advice.exception.PwdAuthFailException;
import com.demo.restapi.entity.Board;
import com.demo.restapi.repository.board.BoardJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardJpaRepository boardJpaRepository;

    /**
     * 게시글 등록
     * @param board
     * @return
     */
    public Board save(Board board) {
        return boardJpaRepository.save(board);
    }

    /**
     * 게시글 목록 조회
     * @return
     */
    public List<Board> getAll() {
        return boardJpaRepository.findAll();
    }

    /**
     * 게시글 단건 조회
     * @param id
     * @return
     */
    public Board getOne(Long id) {
        return boardJpaRepository.findById(id).get();
    }

    /**
     * 게시글 수정
     * @param source
     * @return
     */
    public Board update(Board source) {
        Long boardId = source.getId();
        Board board = getOne(boardId);
        if (!board.getPassword().equals(source.getPassword())) {
            throw new PwdAuthFailException();
        }

        board.setTitle(source.getTitle());
        board.setContext(source.getContext());
        board.setAuthor(source.getAuthor());

        return boardJpaRepository.save(board);
    }

    /**
     * 게시글 삭제
     * @param source
     */
    public boolean delete(Board source) {
        Long boardId = source.getId();
        Board board = getOne(boardId);
        if (!board.getPassword().equals(source.getPassword())) {
            throw new PwdAuthFailException();
        }

        boardJpaRepository.deleteById(boardId);

        return true;
    }

}
