package com.task.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workFlows")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;   // workflow name

    @Column(length = 3000)
    private String description;

    private LocalDateTime createdAt = LocalDateTime.now(); // system time

    // Parent side of relationship
    @OneToMany(mappedBy = "workFlow", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WorkFlowTransaction> transactions = new ArrayList<>();
}