package com.task.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.task.Entity.WorkFlow;
import com.task.Service.WorkFlowService;
import com.task.Repository.WorkFlowRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workFlows")
@RequiredArgsConstructor
public class WorkFlowController {

    @Autowired
    private WorkFlowService workFlowService;

    @Autowired
    private WorkFlowRepository workFlowRepo;

    // 1️) CREATE WORKFLOW
    @PostMapping("/createWorkFlow")
    public ResponseEntity<WorkFlow> createdWorkFlow(@RequestBody WorkFlow workFlow) {
        WorkFlow saved = workFlowService.createWorkFlow(workFlow);
        return ResponseEntity.ok(saved);
    }

    // 2️) GET WORKFLOW BY ID
    @GetMapping("/id/{id}")
    public ResponseEntity<WorkFlow> getWorkFlowById(@PathVariable Long id) {
        return ResponseEntity.ok(workFlowService.getById(id));
    }

    // 3️) GET WORKFLOW BY NAME
    @GetMapping("/name/{name}")
    public ResponseEntity<WorkFlow> getWorkFlowByName(@PathVariable String name) {
        return ResponseEntity.ok(workFlowService.getWorkFlowByName(name));
    }

    // 4️) GET ALL WORKFLOWS
    @GetMapping("/all")
    public ResponseEntity<List<WorkFlow>> getAllWorkFlowBy() {
        return ResponseEntity.ok(workFlowService.getAllWorkFlow());
    }

    // 5️) UPDATE WORKFLOW
    @PutMapping("/updateWorkflow/{id}")
    public ResponseEntity<WorkFlow> updateWorkFlow(@PathVariable Long id,
                                                   @RequestBody WorkFlow wf) {
        WorkFlow updated = workFlowService.updateWorkFlow(id, wf);
        return ResponseEntity.ok(updated);
    }

    // 6️) DELETE WORKFLOW
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        workFlowRepo.deleteById(id);
        return ResponseEntity.ok("WorkFlow Deleted Successfully");
    }
}