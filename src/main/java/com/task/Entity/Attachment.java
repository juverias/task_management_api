package com.task.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Attachment { 
	// Belongs to spring cloud
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Long issueId;
	private String fileName;
	private String contentType;
	private Long sizeBytes;
	private String storagePath;
	private Long cloudId; // cloud id must be connected to project
	private String upLoadedBy;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIssueId() {
		return issueId;
	}
	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Long getSizeBytes() {
		return sizeBytes;
	}
	public void setSizeBytes(Long sizeBytes) {
		this.sizeBytes = sizeBytes;
	}
	public String getStoragePath() {
		return storagePath;
	}
	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}
	public Long getCloudId() {
		return cloudId;
	}
	public void setCloudId(Long cloudId) {
		this.cloudId = cloudId;
	}
	public String getUpLoadedBy() {
		return upLoadedBy;
	}
	public void setUpLoadedBy(String upLoadedBy) {
		this.upLoadedBy = upLoadedBy;
	} 
	

}
