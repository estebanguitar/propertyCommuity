package com.test.propertyCommuity.service;

import com.test.propertyCommuity.dto.BoardDto;
import com.test.propertyCommuity.dto.LikesUserDto;
import com.test.propertyCommuity.repository.BoardRepository;
import com.test.propertyCommuity.repository.LikesUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikesUserBoardService {

    private LikesUserRepository likesUserRepository;
    private BoardRepository boardRepository;

    @Autowired
    public LikesUserBoardService(LikesUserRepository likesUserRepository, BoardRepository boardRepository) {
        this.likesUserRepository = likesUserRepository;
        this.boardRepository = boardRepository;
    }


    @Transactional(rollbackFor = Exception.class)
    public void save(LikesUserDto dto) throws Exception{
        BoardDto boardDto = boardRepository.findById(dto.getBoard().getId()).orElseThrow(() -> new Exception("Board Not Found")).toDto();
        boardDto.setLikes(boardDto.getLikes() + 1L);
        boardRepository.save(boardDto.toEntity());

        dto.setBoard(boardDto.toEntity());
        likesUserRepository.save(dto.toEntity());
    }

}
