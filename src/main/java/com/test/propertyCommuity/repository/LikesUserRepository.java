package com.test.propertyCommuity.repository;

import com.test.propertyCommuity.entity.LikesUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesUserRepository extends JpaRepository<LikesUser, Long> {
    Optional<LikesUser> findByMemberIdAndBoardId(Long userId, Long boardId);
    List<LikesUser> findByMemberId(Long userId);
}
