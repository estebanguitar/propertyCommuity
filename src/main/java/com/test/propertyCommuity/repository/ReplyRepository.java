package com.test.propertyCommuity.repository;

import com.test.propertyCommuity.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByIsDeletedAndBoardId(int isDeleted, Long boardId);
    Optional<Reply> findByIdAndIsDeleted(Long id, int isDeleted);
    Optional<Reply> findByMemberIdAndId(Long userId, Long id);
}
