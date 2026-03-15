package com.task.Entity;

import java.time.LocalDateTime;

import com.task.ENUM.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user_profiles")

@Data 
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileUpdate {
         @Id
         @GeneratedValue(strategy=GenerationType.IDENTITY)
         private Long userId; // for profile update 
         private String userName;
         
         @Column(unique=true)
         private String userOfficialEmail;
         
         private Role role;
         
		 private String department;
         private String designation;
         private String organizationNmae;
         
         private boolean active=true;
         
         private LocalDateTime createdAt=LocalDateTime.now();


		 public Long getUserId() {
			 return userId;
		 }

		 public void setUserId(Long userId) {
			 this.userId = userId;
		 }

		 public String getUserName() {
			 return userName;
		 }

		 public void setUserName(String userName) {
			 this.userName = userName;
		 }

		 public String getUserOfficialEmail() {
			 return userOfficialEmail;
		 }

		 public void setUserOfficialEmail(String userOfficialEmail) {
			 this.userOfficialEmail = userOfficialEmail;
		 }
		 
         public Role getRole() {
			return role;
		}

		 public void setRole(Role role) {
			 this.role = role;
		 }

		 public String getDepartment() {
			 return department;
		 }

		 public void setDepartment(String department) {
			 this.department = department;
		 }

		 public String getDesignation() {
			 return designation;
		 }

		 public void setDesignation(String designation) {
			 this.designation = designation;
		 }

		 public String getOrganizationNmae() {
			 return organizationNmae;
		 }

		 public void setOrganizationNmae(String organizationNmae) {
			 this.organizationNmae = organizationNmae;
		 }

		 public boolean isActive() {
			 return active;
		 }

		 public void setActive(boolean active) {
			 this.active = active;
		 }
         
		 public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		 public void setCreatedAt(LocalDateTime createdAt) {
			 this.createdAt = createdAt;
		 }
         
}
