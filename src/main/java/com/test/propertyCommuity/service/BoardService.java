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

    public List<BoardDto> findAll() {
        return boardRepository.findAll().stream().map(Board::toDto).collect(Collectors.toList());
    }

    public BoardDto findById(Long id) throws Exception{
        return boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not Exist Board")).toDto();
    }

    public BoardDto saveBoard(BoardDto dto) throws Exception {
        return boardRepository.save(dto.toEntity()).toDto();
    }

    public void deleteBoard(Long id) throws Exception {
        BoardDto dto = this.findById(id);
        dto.setIsDeleted(1);
        dto.setDeletedAt(new Date());
        boardRepository.save(dto.toEntity());
    }

//    public BoardDto saveBoardGood(Long id) throws Exception {
//        BoardDto dto = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not Exist Board")).toDto();
//        return boardRepository.save(dto.toEntity()).toDto();
//    }

}