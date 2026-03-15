package com.task.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.Client.IssueClient;
import com.task.ENUM.IssueStatus;

@Service
public class IntegrationService {

    @Autowired
    private IssueClient issueClient;

    // Handles commit messages like "TASK-12 fixed login bug"
    public void handleCommitMessage(String commitMsg, String author) {

        String regex = "([A-Z]+-\\d+)";
        Matcher matcher = Pattern.compile(regex).matcher(commitMsg);

        if (matcher.find()) {

            String issueKey = matcher.group(1);

            Long issueId = Long.parseLong(issueKey.split("-")[1]);

            issueClient.updateStatus(issueId, IssueStatus.DONE, author);

            issueClient.addCommit(issueId, author,
                    "Automatically closed by commit: " + commitMsg);
        }
    }

    // Handles pull request titles like "TASK-12 add validation"
    public void handlePullRequest(String title, String author) {

        String regex = "([A-Z]+-\\d+)";
        Matcher matcher = Pattern.compile(regex).matcher(title);

        if (matcher.find()) {

            String issueKey = matcher.group(1);

            Long issueId = Long.parseLong(issueKey.split("-")[1]);

            issueClient.updateStatus(issueId,
                    IssueStatus.IN_PROGRESS,
                    author);

            issueClient.addCommit(issueId, author,
                    "Pull Request opened: " + title);
        }
    }
}