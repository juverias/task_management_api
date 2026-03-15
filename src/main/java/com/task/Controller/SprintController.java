package com.task.Controller;

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
import com.task.Entity.Sprint;
import com.task.Service.SprintService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sprints")
@RequiredArgsConstructor

public class SprintController {

	@Autowired
	private SprintService sprintService;
	
	// Create Sprint API
	@PostMapping("/create")
	public ResponseEntity<Sprint>create(@RequestBody Sprint sprint){
		return ResponseEntity.ok(sprintService.create(sprint));
	}
	
	// Assign Issue To Sprint
	@PutMapping("/assign/{sprintId}/{issueId}")
	public ResponseEntity<Issue> assignIssue(@PathVariable Long sprintId, @PathVariable Long issueId){
		return ResponseEntity.ok(sprintService.assignIssueToSprint(sprintId, issueId));
	}
	
	// Start Sprint
	@PutMapping("/{sprintId}/start")
	public ResponseEntity<Sprint>startSprint(@PathVariable Long sprintId){
		return ResponseEntity.ok(sprintService.startSprint(sprintId));
	}
	
	// Complete Sprint
	@PutMapping ("/{sprintId}/complete")
	public ResponseEntity <Sprint> completedSprint(@PathVariable Long sprintId){
		return ResponseEntity.ok(sprintService.completeSprint(sprintId));
	}
	
	// Get Burndown Data
	@GetMapping("/{sprintId}/burnDown")
	public ResponseEntity<Map<String, Object>>getBurnDown(@PathVariable Long sprintId){
		return ResponseEntity.ok(sprintService.getBurnDownData(sprintId));
	}
	
	
	
}
