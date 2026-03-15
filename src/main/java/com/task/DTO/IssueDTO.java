package com.task.DTO;

import java.time.LocalDateTime;

import com.task.ENUM.IssuePriority;
import com.task.ENUM.IssueStatus;
import com.task.ENUM.IssueType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueDTO {

    private Long id;
    private String issueKey;

    @NotBlank(message = "Issue title is required")
    private String issueTitle;

    private String issueDescription;

    @NotNull(message = "Issue type is required")
    private IssueType issueType;

    private IssueStatus issueStatus;
    private IssuePriority issuePriority;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;

    private String assignedEmail;
    private String reporterEmail;

    private Long sprintId;
    private Long epicId;

}