package com.task.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.task.Entity.WorkFlow;
import com.task.Entity.WorkFlowTransaction;
import com.task.Repository.WorkFlowTransactionRepository;
import com.task.Repository.WorkFlowRepository;

@Service
public class WorkFlowService {

    @Autowired
    private WorkFlowRepository workFlowRepo;

    @Autowired
    private WorkFlowTransactionRepository transactionRepo;

    // NOTE : Work Flow always depend on task
    public WorkFlow createWorkFlow(WorkFlow wf) {

        if(workFlowRepo.findByName(wf.getName()).isPresent()){
            throw new RuntimeException("Workflow with this name already exists");
        }

        if (wf.getTransactions() != null) {
            for (WorkFlowTransaction t : wf.getTransactions()) {
                t.setWorkFlow(wf);
            }
        }

        return workFlowRepo.save(wf);
    }

    @Transactional
    public WorkFlow updateWorkFlow(Long id, WorkFlow updated) {

        WorkFlow wf = getById(id);

        wf.setName(updated.getName());
        wf.setDescription(updated.getDescription());

        wf.getTransactions().clear();

        if (updated.getTransactions() != null) {
            for (WorkFlowTransaction t : updated.getTransactions()) {
                t.setWorkFlow(wf);
                wf.getTransactions().add(t);
            }
        }

        return workFlowRepo.save(wf);
    }
    
    public List<WorkFlow> getAllWorkFlow() {
        return workFlowRepo.findAll();
    }
    
    public WorkFlow getById(Long id) {
        return workFlowRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkFlow Not Found"));
    }

    public WorkFlow getWorkFlowByName(String name) {
        return workFlowRepo.findByName(name)
                .orElseThrow(() -> new RuntimeException("Work Flow Not Found"));
    }

	public WorkFlowTransactionRepository getTransactionRepo() {
		return transactionRepo;
	}

	public void setTransactionRepo(WorkFlowTransactionRepository transactionRepo) {
		this.transactionRepo = transactionRepo;
	}
}
