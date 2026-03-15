package com.task.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.task.DTO.IssueDTO;
import com.task.ENUM.IssueStatus;
import com.task.Entity.IssueComment;
import com.task.Service.IssueService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor

public class IssueController {

	private final IssueService issueService;

    // Create Issue
    @PostMapping("/create")
    public ResponseEntity<IssueDTO> create(@RequestBody IssueDTO issue){
        return ResponseEntity.ok(issueService.createIssue(issue));
    }

    // Get Issue by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<IssueDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(issueService.getById(id));
    }

    // Get Issues by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<List<IssueDTO>> getByEmail(@PathVariable String email){
        return ResponseEntity.ok(issueService.getByAssignedEmail(email));
    }

    // Add Comment to Issue
    @PostMapping("/{id}/commit")
    public ResponseEntity<IssueComment> addComment(
            @PathVariable Long id,
            @RequestParam String author,
            @RequestParam String body){
        return ResponseEntity.ok(issueService.addComment(id, author, body));
    }
    

    // Update Issue Status
    @PutMapping("/{id}/status")
    public ResponseEntity<IssueDTO> updateStatus(@PathVariable Long id,
                                                 @RequestParam IssueStatus status,
                                                 @RequestParam String performedBy){
        return ResponseEntity.ok(issueService.updateIssueStatus(id, status, performedBy));
    }

    // Get Issues by Sprint
    @GetMapping("/sprint/{sprintId}")
    public ResponseEntity<List<IssueDTO>> getBySprint(@PathVariable Long sprintId){
        return ResponseEntity.ok(issueService.findBySprint(sprintId));
    }

      // Search Issues
    @GetMapping("/search")
    public ResponseEntity<List<IssueDTO>> search(@RequestParam Map<String,String> filter){
        return ResponseEntity.ok(issueService.search(filter));
    }
}