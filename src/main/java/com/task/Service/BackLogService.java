package com.task.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.task.ENUM.IssueType;
import com.task.ENUM.SprintState;
import com.task.Entity.Issue;
import com.task.Entity.Sprint;
import com.task.Repository.IssueRepository;
import com.task.Repository.SprintRepository;

@Service
public class BackLogService {

    @Autowired
    private IssueRepository issueRepo;

    @Autowired
    private SprintRepository sprintRepo;

    // Get Backlog Issues
    public List<Issue> getBackLog(Long projectId) {
        return issueRepo.findByProjectIdAndSprintIdIsNullOrderByBackLogPosition(projectId);
    }

    // Record backlog order
    @Transactional
    public void recordBackLog(Long projectId, List<Long> orderedIssueIds) {

        int position = 0;

        for (Long issueId : orderedIssueIds) {

            Issue issue = issueRepo.findById(issueId)
                    .orElseThrow(() -> new RuntimeException("Issue not found"));

            issue.setBackLogPosition(position++);
            issueRepo.save(issue);
        }
    }

    // Add Issue to Sprint
    @Transactional
    public Issue addIssueToSprint(Long issueId, Long sprintId) {

        Issue issue = issueRepo.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        Sprint sprint = sprintRepo.findById(sprintId)
                .orElseThrow(() -> new RuntimeException("Sprint not found"));

        SprintState state = sprint.getState();

        if (state != SprintState.PLANNED && state != SprintState.ACTIVE) {
            throw new RuntimeException("Cannot add issue to sprint in state: " + state);
        }

        issue.setSprintId(sprintId);
        issue.setBackLogPosition(null);

        return issueRepo.save(issue);
    }

    // Get Backlog Hierarchy
    public Map<String, Object> getBackLogHierarchy(Long projectId) {

        List<Issue> backLog = getBackLog(projectId);

        Map<Long, Map<String, Object>> epicMap = new HashMap<>();
        Map<Long, Issue> storyMap = new HashMap<>();

        // Step 1: Identify EPICS and STORIES
        for (Issue issue : backLog) {

            if (IssueType.EPICS.equals(issue.getIssueType())) {

                Map<String, Object> epicData = new HashMap<>();

                epicData.put("epic", issue);
                epicData.put("stories", new ArrayList<Issue>());
                epicData.put("subtasks", new HashMap<Long, List<Issue>>());

                epicMap.put(issue.getId(), epicData);
            }

            if (IssueType.STORIES.equals(issue.getIssueType())) {
                storyMap.put(issue.getId(), issue);
            }
        }

        // Step 2: Attach stories to epics
        for (Issue issue : backLog) {

            if (IssueType.STORIES.equals(issue.getIssueType())) {

                Long epicId = issue.getParentIssueId();

                if (epicId != null && epicMap.containsKey(epicId)) {

                    List<Issue> stories =
                            (List<Issue>) epicMap.get(epicId).get("stories");

                    stories.add(issue);
                }
            }
        }

        // Step 3: Attach subtasks to stories
        for (Issue issue : backLog) {

            if (IssueType.SUB_TASKS.equals(issue.getIssueType())) {

                Long parentId = issue.getParentIssueId();

                if (parentId != null && storyMap.containsKey(parentId)) {

                    for (Map<String, Object> epicData : epicMap.values()) {

                        List<Issue> stories =
                                (List<Issue>) epicData.get("stories");

                        boolean belongsToEpic =
                                stories.stream()
                                        .anyMatch(s -> s.getId().equals(parentId));

                        if (belongsToEpic) {

                            Map<Long, List<Issue>> subtaskMap =
                                    (Map<Long, List<Issue>>) epicData.get("subtasks");

                            subtaskMap
                                    .computeIfAbsent(parentId, k -> new ArrayList<>())
                                    .add(issue);

                            break;
                        }
                    }
                }
            }
        }

        return Collections.singletonMap("epics", epicMap);
    }
}