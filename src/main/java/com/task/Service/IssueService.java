package com.task.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.task.DTO.IssueDTO;
import com.task.ENUM.IssueStatus;
import com.task.Entity.Issue;
import com.task.Entity.IssueComment;
import com.task.Repository.IssueCommentRepository;
import com.task.Repository.IssueRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepo;
    private final IssueCommentRepository issueCommentRepo;

    // Create new Issue
    
    @Transactional
    public IssueDTO createIssue(IssueDTO dto) {

        Issue issue = new Issue();

        issue.setIssueTitle(dto.getIssueTitle());
        issue.setIssueDescription(dto.getIssueDescription());
        issue.setIssueType(dto.getIssueType());
        issue.setPriority(dto.getIssuePriority());
        issue.setIssueStatus(dto.getIssueStatus());
        issue.setAssignedEmail(dto.getAssignedEmail());
        issue.setReporterEmail(dto.getReporterEmail());
        issue.setDueDate(dto.getDueDate());

        // FIX (IMPORTANT)
        issue.setSprintId(dto.getSprintId());
        issue.setEpicId(dto.getEpicId());

        // Generate unique Issue Key
        issue.setIssueKey("PROJECT-" + System.currentTimeMillis());

        issue = issueRepo.save(issue);

        return toDTO(issue);
    }
    /**
     * Add Comment to Issue
     */
    @Transactional
    public IssueComment addComment(Long issueId, String authorEmail, String body) {

        Issue issue = issueRepo.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        IssueComment comment = new IssueComment();
        comment.setIssueId(issueId);
        comment.setAuthourEmail(authorEmail);
        comment.setBody(body);

        return issueCommentRepo.save(comment);
    }

    /**
     * Get Issue by ID
     */
    public IssueDTO getById(Long id) {

        Issue issue = issueRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        return toDTO(issue);
    }

    /**
     * Get Issues assigned to a user
     */
    public List<IssueDTO> getByAssignedEmail(String email) {

        return issueRepo.findByAssignedEmail(email)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get Issues by Sprint
     */
    public List<IssueDTO> findBySprint(Long sprintId) {

        return issueRepo.findBySprintId(sprintId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update Issue Status
     */
    @Transactional
    public IssueDTO updateIssueStatus(Long id, IssueStatus status, String performedBy) {

        Issue issue = issueRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        issue.setIssueStatus(status);

        issue = issueRepo.save(issue);

        return toDTO(issue);
    }

    
    // Search Issues
    
    public List<IssueDTO> search(Map<String, String> filters) {

        // Search by title
        if (filters.containsKey("issueTitle")) {
            String title = filters.get("issueTitle").toLowerCase();

            return issueRepo.findAll()
                    .stream()
                    .filter(issue -> issue.getIssueTitle().equalsIgnoreCase(title))
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }

        // Search by assignee
        if (filters.containsKey("assignee")) {
            return getByAssignedEmail(filters.get("assignee"));
        }

        // Search by sprint
        if (filters.containsKey("sprint")) {
            Long sprintId = Long.valueOf(filters.get("sprint"));
            return findBySprint(sprintId);
        }

        // Search by status
        if (filters.containsKey("status")) {
            IssueStatus status = IssueStatus.valueOf(filters.get("status"));
            return issueRepo.findByIssueStatus(status)
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }

        return issueRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    // Convert Entity → DTO
    
    private IssueDTO toDTO(Issue issue) {

        IssueDTO dto = new IssueDTO();

        dto.setId(issue.getId());
        dto.setIssueKey(issue.getIssueKey());
        dto.setIssueTitle(issue.getIssueTitle());
        dto.setIssueDescription(issue.getIssueDescription());
        dto.setIssueType(issue.getIssueType());
        dto.setIssuePriority(issue.getPriority());
        dto.setIssueStatus(issue.getIssueStatus());

        dto.setAssignedEmail(issue.getAssignedEmail());
        dto.setReporterEmail(issue.getReporterEmail());

        dto.setSprintId(issue.getSprintId());
        dto.setEpicId(issue.getEpicId());

        dto.setCreatedAt(issue.getCreatedAt());
        dto.setUpdatedAt(issue.getUpdatedAt());
        dto.setDueDate(issue.getDueDate());

        return dto;
    }
}