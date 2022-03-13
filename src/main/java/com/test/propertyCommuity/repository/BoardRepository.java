package com.test.propertyCommuity.repository;

import com.test.propertyCommuity.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByIsDeleted(int isDeleted);
    Optional<Board> findByIdAndIsDeleted(Long id, int isDeleted);
}
