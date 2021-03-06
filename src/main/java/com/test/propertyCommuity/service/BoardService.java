package com.test.propertyCommuity.service;

import com.test.propertyCommuity.dto.BoardDto;
import com.test.propertyCommuity.entity.Board;
import com.test.propertyCommuity.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {this.boardRepository = boardRepository;}

    public List<BoardDto> findAll(int isDeleted) {
        return boardRepository.findByIsDeleted(isDeleted).stream().map(Board::toDto).collect(Collectors.toList());
    }

    public BoardDto findByIdAndMemberIdAndParentId(Long id, Long userId, Long parentId) throws Exception{
        return boardRepository.findByIdAndMemberIdAndParentId(id, userId, parentId).toDto();
    }

    public List<BoardDto> findAllReply(int isDeleted, Long parentId) {
        return boardRepository.findByIsDeletedAndParentId(isDeleted, parentId).stream().map(Board::toDto).collect(Collectors.toList());
    }

    public BoardDto findById(Long id, int isDeleted) throws Exception{
        return boardRepository.findByIdAndIsDeleted(id, isDeleted).orElseThrow(() -> new NoSuchElementException("Not Exist Board")).toDto();
    }

    public boolean isUsersBoard(Long userId, Long boardId) {
        return boardRepository.findByMemberIdAndId(userId, boardId).isPresent();
    }

    public BoardDto saveBoard(BoardDto dto) throws Exception {
        return boardRepository.save(dto.toEntity()).toDto();
    }

    public void deleteBoard(Long id) throws Exception {
        BoardDto dto = boardRepository.findById(id).get().toDto();
        dto.setIsDeleted(1);
        dto.setDeletedAt(new Date());
        boardRepository.save(dto.toEntity());
    }

}
