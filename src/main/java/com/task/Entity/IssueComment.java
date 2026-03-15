package com.task.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(
    name = "issue-comments",
    indexes = { @Index(name = "idx_comment_issue", columnList = "issue_id") }
)
public class IssueComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "issue_id", nullable = false)
    private Long issueId;

    @Column(nullable = false)
    private String authourEmail;

    @Column(length = 5000)
    private String body;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getIssueId() { return issueId; }

    public void setIssueId(Long issueId) { this.issueId = issueId; }

    public String getAuthourEmail() { return authourEmail; }

    public void setAuthourEmail(String authourEmail) { this.authourEmail = authourEmail; }

    public String getBody() { return body; }

    public void setBody(String body) { this.body = body; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
