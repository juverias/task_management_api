package com.task.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.task.ENUM.IssueStatus;
import com.task.ENUM.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "workFlow_Transactions",
    indexes = {
        @Index(name = "id_wf_from_to", columnList = "workFlow_id,fromStatus,toStatus")
    }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkFlowTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Child side of relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workFlow_id", nullable = false)
    @JsonBackReference
    private WorkFlow workFlow;

    private IssueStatus fromStatus;
    private IssueStatus toStatus;

    private String name;

    private Role allowedRole;
}