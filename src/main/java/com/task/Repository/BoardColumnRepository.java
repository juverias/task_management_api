package com.task.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task.Entity.BoardColumns;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumns,Long> {

	List<BoardColumns>findByBoardIdOrderByPosition(Long boardId);
	
}
