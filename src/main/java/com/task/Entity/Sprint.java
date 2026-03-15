package com.task.Entity;

import java.time.LocalDateTime;

import com.task.ENUM.SprintState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="sprints")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Sprint {

	// for sprint segregation inside the issue  
			@Id
			@GeneratedValue(strategy=GenerationType.IDENTITY)
			private Long id ;
			
			private String sprintName;
			
			private LocalDateTime startDate;
			private LocalDateTime endDate;
			
			// issue - status , sprint - state 
			@Enumerated(EnumType.STRING)
			private SprintState state;
			
			@Column(length=2000)
			private String sprintDescription;
			
			private LocalDateTime createdAt= LocalDateTime.now();
			
			private Long projectId;
	
	// Epic Features , Optional 
	        
	public Long getProjectId() {
				return projectId;
			}

			public void setProjectId(Long projectId) {
				this.projectId = projectId;
			}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSprintName() {
		return sprintName;
	}

	public void setSprintName(String sprintName) {
		this.sprintName = sprintName;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public SprintState getState() {
		return state;
	}

	public void setState(SprintState state) {
		this.state = state;
	}

	public String getSprintDescription() {
		return sprintDescription;
	}

	public void setSprintDescription(String sprintDescription) {
		this.sprintDescription = sprintDescription;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
}
