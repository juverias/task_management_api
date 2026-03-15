package com.task.Controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.Entity.Issue;
import com.task.Service.BackLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/back_log")
@RequiredArgsConstructor

public class BackLogController {
	
	@Autowired
	private BackLogService backLogService;
	
	// Get Backlog API
	@GetMapping("/{projectId}")
	public ResponseEntity<List<Issue>> getBackLog(@PathVariable Long projectId){
	    return ResponseEntity.ok(backLogService.getBackLog(projectId));
	}

	// Record Backlog Order 
	@PostMapping("/{projectId}/record")
	public ResponseEntity<String>record(@PathVariable(required=false)Long projectId,
			                           @RequestBody List<Long>orderedIssueIds){
		backLogService.recordBackLog(projectId, orderedIssueIds);
		return ResponseEntity.ok("BackLog recorded");
	}
	
	// Add Issue To Sprint API
	@PutMapping("/add-to-sprint/{issueId}/{sprintId}")
	public ResponseEntity<Issue>addIssueToSprint(@PathVariable Long issueId,
			                                     @PathVariable Long sprintId){
		return ResponseEntity.ok(backLogService.addIssueToSprint(issueId, sprintId));
	}
	
	// Get Backlog Hierarchy API
	@GetMapping("/{projectId}/hierarchy")
	public ResponseEntity<Map<String,Object>>getHierarchy(@PathVariable(required=false) Long projectId){
		return ResponseEntity.ok(backLogService.getBackLogHierarchy(projectId));
	}
}
