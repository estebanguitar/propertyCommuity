package com.test.propertyCommuity.service;

import com.test.propertyCommuity.dto.LikesUserDto;
import com.test.propertyCommuity.entity.LikesUser;
import com.test.propertyCommuity.repository.LikesUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikesUserService {

    private LikesUserRepository likesUserRepository;

    @Autowired
    public LikesUserService(LikesUserRepository likesUserRepository) {this.likesUserRepository = likesUserRepository;}


    public LikesUserDto save(LikesUserDto dto) throws DataIntegrityViolationException,Exception{
        return likesUserRepository.save(dto.toEntity()).toDto();
    }

    public List<LikesUserDto> findAll() {
        return likesUserRepository.findAll().stream().map(LikesUser::toDto).collect(Collectors.toList());
    }
//
    public boolean findByUserId(Long id) throws Exception{
        return likesUserRepository.findByMemberId(id).isPresent();
    }
//
//    public BoardDto saveBoard(BoardDto dto) throws Exception {
//        return boardRepository.save(dto.toEntity()).toDto();
//    }
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
