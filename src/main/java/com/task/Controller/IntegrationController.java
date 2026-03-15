package com.task.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.task.Service.IntegrationService;

@RestController
@RequestMapping("/api/integrations")
public class IntegrationController {

    @Autowired
    private IntegrationService integrationService;

    // 1️) Commit Integration API
    @PostMapping("/commit")
    public ResponseEntity<?> handleCommit(
            @RequestParam String message,
            @RequestParam String author) {

        integrationService.handleCommitMessage(message, author);

        return ResponseEntity.ok("Commit processed");
    }

    // 2️) Pull Request Integration API
    @PostMapping("/pullRequest")
    public ResponseEntity<?> handlePR(
            @RequestParam String title,
            @RequestParam String author) {

        integrationService.handlePullRequest(title, author);

        return ResponseEntity.ok("Pull request processed");
    }
}