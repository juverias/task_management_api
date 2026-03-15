package com.task.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.task.ENUM.IssueStatus;
import com.task.ENUM.SprintState;
import com.task.Entity.Issue;
import com.task.Entity.Sprint;
import com.task.Repository.IssueRepository;
import com.task.Repository.SprintRepository;

@Service
public class SprintService {

	@Autowired
	private SprintRepository sprintRepo;
	
	@Autowired
	private IssueRepository issueRepo;
	
	public Sprint create(Sprint sprint) {
		sprint.setState(SprintState.PLANNED);
		return sprintRepo.save(sprint);
	}
	
	// Assign details in task area for completion of sprint 
	@Transactional 
	public Issue assignIssueToSprint(Long sprintId,Long issueId) {
		Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(()-> new RuntimeException("Sprint not found"));
		Issue issue = issueRepo.findById(issueId).orElseThrow(()-> new RuntimeException("Issue not found"));

		if(sprint.getState()==SprintState.COMPLETED) {
			throw new RuntimeException("Cannot add task to completed sprint");
		}
		
		issue.setSprintId(sprintId);
		return issueRepo.save(issue);
	}
	
	// How to start the sprint 
	@Transactional 
	public Sprint startSprint(Long sprintId) {
		Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(()->new RuntimeException("Sprint not found "));
		
		if(sprint.getState() != SprintState.PLANNED) {
			throw new RuntimeException("Sprint cannot be started");	
		}
		
		sprint.setState(SprintState.ACTIVE);
		
		if(sprint.getStartDate()==null) {
			sprint.setStartDate(LocalDateTime.now());
		}
		return sprintRepo.save(sprint);
	}
	
	// Close the sprint / Complete Sprint 
	@Transactional 
	public Sprint completeSprint(Long sprintId) {
		Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(()->new RuntimeException("Sprint not found "));

		sprint.setState(SprintState.COMPLETED);

		if(sprint.getState()==null) {
			sprint.setEndDate(LocalDateTime.now());
		}
		
		List<Issue> issues = issueRepo.findBySprintId(sprintId);
		
		for(Issue i : issues) {
			if(i.getIssueStatus()!=IssueStatus.DONE) {
				i.setSprintId(null);
				issueRepo.save(i);
			}
		}
		
		return sprintRepo.save(sprint);
	}
	
	// Ask client for more time (Optional one but required sometimes)
	public Map<String,Object>getBurnDownData(Long sprintId){
		Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(()->new RuntimeException("Sprint not found "));

		LocalDateTime start = sprint.getStartDate();
		LocalDateTime end = sprint.getEndDate() !=null ? sprint.getEndDate():LocalDateTime.now();
		
		List<Issue> issues = issueRepo.findBySprintId(sprintId);
		
		int totalTasks = issues.size();
		
		Map<String,Object> chart = new LinkedHashMap<>();
		
		LocalDateTime curser=start;
		
		while(!curser.isAfter(end)) {
			int completed = (int) issues.stream().filter(i-> i.getIssueStatus()==IssueStatus.DONE).count();
			chart.put(curser.toString(),totalTasks - completed);
			curser=curser.plusDays(1);
			
		}
		Map<String,Object> response = new HashMap<>();
		response.put("SprintId", sprintId);
		response.put("StartDate", start);
		response.put("EndDate", end);
		response.put("BurnDown", chart);
		
		return response;
		
	}
	
	   
}
