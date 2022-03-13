package com.test.propertyCommuity.service;

import com.test.propertyCommuity.dto.LikesDto;
import com.test.propertyCommuity.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class LikesService {

    private LikesRepository likesRepository;

    @Autowired
    public LikesService(LikesRepository likesRepository) {this.likesRepository = likesRepository;}


//    public List<BoardDto> findAll() {
//        return boardRepository.findAll().stream().map(Board::toDto).collect(Collectors.toList());
//    }
//
    public LikesDto findById(Long id) throws Exception{
        return likesRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not Exist Likes")).toDto();
    }
//
    public LikesDto save(LikesDto dto) throws Exception {
        return likesRepository.save(dto.toEntity()).toDto();
    }
//
//    public void deleteBoard(Long id) throws Exception {
//        BoardDto dto = this.findById(id);
//        dto.setIsDeleted(1);
//        dto.setDeletedAt(new Date());
//        boardRepository.save(dto.toEntity());
//    }

//    public BoardDto saveBoardGood(Long id) throws Exception {
//        BoardDto dto = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not Exist Board")).toDto();
//        return boardRepository.save(dto.toEntity()).toDto();
//    }

}
