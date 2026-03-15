package com.task.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.Entity.Board;
import com.task.Entity.BoardCard;
import com.task.Entity.BoardColumns;
import com.task.Service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
	@PostMapping("/create")
	public ResponseEntity<Board>createBoard(@RequestBody Board board){
		return ResponseEntity.ok(boardService.createBoard(board));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Board>> getBoard(@PathVariable Long id){
		return ResponseEntity.ok(boardService.getByBoardId(id));
	}

	@GetMapping("/{id}/column")
	public ResponseEntity<List<BoardColumns>> getBoardColumn(@PathVariable Long id){
		
		return ResponseEntity.ok(boardService.getColumns(id));
	}
	
	@PostMapping("/{id}/column")
	public ResponseEntity<BoardColumns> addColumn(
	        @PathVariable Long id,
	        @RequestBody BoardColumns column){

	    return ResponseEntity.ok(boardService.addColumn(id, column));
	}
	
	@PostMapping("/{boardId}/columns/{columnId}/issues/{issueId}")
	public ResponseEntity<BoardCard> addCard(
	        @PathVariable Long boardId,
	        @PathVariable Long columnId,
	        @PathVariable Long issueId) {

	    return ResponseEntity.ok(
	        boardService.addIssueToBoard(boardId, columnId, issueId)
	    );
	}
	
	@PostMapping("/{boardId}/columns/{columnId}/cards/{cardId}/move/{position}/{performedBy}")
	public ResponseEntity<String> moveCard(
	        @PathVariable Long boardId,
	        @PathVariable Long columnId,
	        @PathVariable Long cardId,
	        @PathVariable int position,
	        @PathVariable String performedBy) {

	    boardService.moveCards(boardId, columnId, cardId, position, performedBy);
	    return ResponseEntity.ok("Moved");
	}
	
	@PostMapping("/{boardId}/columns/{columnId}/record")
	public ResponseEntity<String> recordColumn(
	        @PathVariable Long boardId,
	        @PathVariable Long columnId,
	        @RequestBody List<Long> orderCardId) {

	    boardService.recordColumn(boardId, columnId, orderCardId);
	    return ResponseEntity.ok("Recorded");
	}
	
	@PostMapping("/sprints/{sprintId}/start")
	public ResponseEntity<String>startSprint(@PathVariable Long sprintId){
		boardService.startSprint(sprintId);
		return ResponseEntity.ok("Sprint Started");
	}
	
	@PostMapping("/sprints/{sprintId}/complete")
	public ResponseEntity<String>completeSprint(@PathVariable Long sprintId){
		boardService.completeSprint(sprintId);
		
		return ResponseEntity.ok("Complete Sprint");
	}
}


