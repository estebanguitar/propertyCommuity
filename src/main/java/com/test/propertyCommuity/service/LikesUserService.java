package com.test.propertyCommuity.service;

import com.test.propertyCommuity.dto.LikesUserDto;
import com.test.propertyCommuity.entity.LikesUser;
import com.test.propertyCommuity.repository.LikesUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikesUserService {

    private LikesUserRepository likesUserRepository;

    @Autowired
    public LikesUserService(LikesUserRepository likesUserRepository) {
        this.likesUserRepository = likesUserRepository;
    }

    public List<LikesUserDto> findAll() {
        return likesUserRepository.findAll().stream().map(LikesUser::toDto).collect(Collectors.toList());
    }

    public boolean findByUserIdAndBoardId(Long userId, Long boardId) throws Exception{
        return likesUserRepository.findByMemberIdAndBoardId(userId, boardId).isPresent();
    }
    public List<LikesUserDto> findByUserId(Long userId) throws Exception{
        return likesUserRepository.findByMemberId(userId).stream().map(LikesUser::toDto).collect(Collectors.toList());
    }
}
