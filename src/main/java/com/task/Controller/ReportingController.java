package com.task.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.task.Service.ReportingService;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {

    @Autowired
    private ReportingService reportingService;
    
    // 1) Test Burndown Chart API
    @GetMapping("/burndown/{sprintId}")
    public ResponseEntity<Map<String, Object>> burnDown(@PathVariable Long sprintId) {
        return ResponseEntity.ok(reportingService.burndown(sprintId));
    }

    // 2) Test Sprint Report API
    @GetMapping("/sprintReport/{sprintId}")
    public ResponseEntity<Map<String, Object>> sprintReport(@PathVariable Long sprintId) {
        return ResponseEntity.ok(reportingService.sprintReport(sprintId));
    }

    //3) Test Workload Report
    @GetMapping("/workload/{sprintId}")
    public ResponseEntity<Map<String, Object>> workload(@PathVariable Long sprintId) {
        return ResponseEntity.ok(reportingService.workLoad(sprintId));
    }
    
    // 4) Test Cumulative Flow
    @GetMapping("/cumulative-flow/{sprintId}")
    public ResponseEntity<Map<String, Object>> cumulativeFlow(@PathVariable Long sprintId) {
        return ResponseEntity.ok(reportingService.cumulativeFlow(sprintId));
    }
    
    // 5) Test Epic Report
    @GetMapping("/epicReport/{epicId}")
    public ResponseEntity<Map<String, Object>> epicReport(@PathVariable Long epicId) {
        return ResponseEntity.ok(reportingService.epicReport(epicId));
    }
    
    // 6) Test Velocity Report
    @GetMapping("/velocity/{projectId}")
    public ResponseEntity<Map<String, Object>> velocity(@PathVariable Long projectId) {
        return ResponseEntity.ok(reportingService.velocity(projectId));
    }

}