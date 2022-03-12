package com.test.propertyCommuity.repository;

import com.test.propertyCommuity.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
