package com.task.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.task.ENUM.IssueStatus;
import com.task.Entity.Board;
import com.task.Entity.BoardCard;
import com.task.Entity.BoardColumns;
import com.task.Entity.Issue;
import com.task.Repository.BoardCardRepository;
import com.task.Repository.BoardColumnRepository;
import com.task.Repository.BoardRepository;
import com.task.Repository.IssueRepository;

@Service
public class BoardService {
 
	
	@Autowired
	private BoardRepository boardRepo;
	
	
	@Autowired
	private BoardColumnRepository columnRepo;
	
	
	@Autowired
	private BoardCardRepository cardRepo;
	
	@Autowired
	private IssueRepository issueRepo;
	
	
	public Board createBoard(Board br) {
		return boardRepo.save(br);
	}
	
	public BoardColumns addColumn(Long boardId, BoardColumns column) {

	    Board board = boardRepo.findById(boardId)
	            .orElseThrow(() -> new RuntimeException("Board not found"));

	    column.setBoard(board);

	    return columnRepo.save(column);
	}

	public Optional<Board> getByBoardId(Long id){
		return boardRepo.findById(id);
	}
	
	public List<BoardColumns>getColumns(Long boardId){
		return columnRepo.findByBoardIdOrderByPosition(boardId);
	}
	
	public List<BoardCard>getCardsForColumn(Long boardId,Long columnId){
		return cardRepo.findByBoardIdAndColumnIdOrderByPosition(boardId,columnId );
	}
	
	@Transactional 
	public BoardCard addIssueToBoard(Long boardId, Long columnId, Long issueId) {
		
		// Check issue is available /not 
		Issue issue=issueRepo.findById(issueId).orElseThrow(()-> new RuntimeException("Issue not Found"));
		
		// Check issue generated in column / not 
		// BoardColumns column = columnRepo.findById(columnId).orElseThrow(()-> new RuntimeException("Column not Found"));
		
		// Check issue present / not - If not how we can add it or else how we can update it 
		cardRepo.findByIssueId(issueId).ifPresent(cardRepo::delete);
	   
         BoardColumns column= columnRepo.findById(columnId).orElseThrow(()-> new RuntimeException("Column not found"));
		
		if(column.getWiplimit() !=null && column.getWiplimit()> 0) {
			long count = cardRepo.countByBoardIdAndColumnId(boardId, columnId);
			
			if(count >= column.getWiplimit()) {
				throw new RuntimeException("WIP limit reached for column:"+column.getName());
				
			}
		}
		
		// How to generate board 
		List<BoardCard> existing = cardRepo.findByBoardIdAndColumnIdOrderByPosition(boardId, columnId);
		
		int postion= existing.size();
		
		BoardCard card= new BoardCard();
		card.setBoardId(boardId);
		card.setColumn(column);
		card.setIssueId(issueId);
		card.setPosition(postion);
		
		card= cardRepo.save(card);
		
//		if(column.getStatusKey() !=null) {
//			issue.setIssueStatus(Enum.valueOf(com.TaskManage.Enum.IssueStatus.class, column.getStatusKey()));
//			issueRepo.save(issue);
//		}
		
//		IssueStatus statusKey= column.getStatusKey();
//		
//		if(statusKey!=null && !statusKey.isEmpty()) {}
//		
//		IssueStatus status= IssueStatus.valueOf(statusKey.trim().toUpperCase());
//		
//		issue.setIssueStatus(status);
//		issueRepo.save(issue);
//		
		
		return card;
		
		
	}
	
	// Positions
	@Transactional
	public void moveCards(Long boardId,Long columnId,Long CardId,int position,String performedBy) {
		
		BoardCard card= cardRepo.findById(CardId).orElseThrow(()-> new RuntimeException("Cards not avilable"));
		
		BoardColumns fromColumn= card.getColumn();
		BoardColumns toColumn= columnRepo.findById(columnId).orElseThrow(()-> new RuntimeException("column not found"));
		
		// For Adding the Columns 
		if(toColumn.getWiplimit()!=null && toColumn.getWiplimit()>0 ) {
			long count =cardRepo.countByBoardIdAndColumnId(boardId, columnId);
			
			if(!Objects.equals(fromColumn.getId(), toColumn.getId()) && count >=toColumn.getWiplimit() ) {
			throw new RuntimeException("Wip limit exceeded for column:"+toColumn.getName());
			}
			
		}
		
		// To give details 
		List<BoardCard>fromCards= cardRepo.findByBoardIdAndColumnIdOrderByPosition(boardId,fromColumn.getId() );
		
		for(BoardCard c : fromCards) {
			if(c.getPosition()> card.getPosition() ) {
				c.setPosition(c.getPosition()-1);
				cardRepo.save(c);
			}
		}
		
		List<BoardCard>toCards=cardRepo.findByBoardIdAndColumnIdOrderByPosition(boardId,toColumn.getId());
		
		for(BoardCard c : toCards) {
			if(c.getPosition()> card.getPosition() ) {
				c.setPosition(c.getPosition()+1);
				cardRepo.save(c);
			}
		}
		
		issueRepo.findById(card.getIssueId()).ifPresent(issue-> updateIssueStatus(issue,toColumn.getStatusKey()));
	}
	
	
	private void updateIssueStatus(Issue issue,IssueStatus issueStatus ) {
		if(issueStatus==null ||issueStatus.isEmpty() ) {
			return;
		}
		try {
		
		issue.setIssueStatus(issueStatus);
		issueRepo.save(issue);
		}catch(Exception e) {
			throw new RuntimeException("Invalid statusKey mapping:"+issueStatus,e);
		}
	}
	
	@Transactional
	public void recordColumn(Long boardId,Long columnId,List<Long> orderedCardId) {
		int postion= 0;
		for(Long cardId : orderedCardId) {
			BoardCard card= cardRepo.findById(cardId).orElseThrow(()-> new RuntimeException("card not avialable"));
			
			card.setPosition(postion);
			cardRepo.save(card);
		}
	}
	
	@Transactional
	public void startSprint(Long sprintId) {}
	
	@Transactional
    public void completeSprint(Long sprintId) {}


}

