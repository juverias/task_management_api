package com.task.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.task.Config.FeignConfig;
import com.task.ENUM.IssueStatus;

@FeignClient(
        name = "issue-service",
        url = "${issue_service.url}",
        configuration = FeignConfig.class
)
public interface IssueClient {

    // Calls: http://localhost:8080/api/issues/{id}/status
    @PutMapping("/{id}/status")
    void updateStatus(@PathVariable Long id,
                      @RequestParam IssueStatus status,
                      @RequestParam String performedBy);

    // Calls: http://localhost:8080/api/issues/{id}/commit
    @PostMapping("/{id}/commit")
    void addCommit(@PathVariable Long id,
                   @RequestParam String author,
                   @RequestParam String body);
}