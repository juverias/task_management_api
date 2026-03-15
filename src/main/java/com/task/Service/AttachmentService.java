package com.task.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.task.Entity.Attachment;
import com.task.Repository.AttachmentRepository;
import com.task.Storage.StorageService;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachRepo;

    @Autowired
    private StorageService storage;

    /**
     * Upload file to Cloudinary
     */
    @Transactional
    public Attachment upload(Long issueId, MultipartFile file, String uploadedBy) {

        String cloudURL = storage.store(file, "issues/" + issueId);

        Attachment attach = new Attachment();

        attach.setIssueId(issueId);
        attach.setFileName(file.getOriginalFilename());
        attach.setContentType(file.getContentType());
        attach.setSizeBytes(file.getSize());
        attach.setStoragePath(cloudURL);
        attach.setUpLoadedBy(uploadedBy);

        return attachRepo.save(attach);
    }

    /**
     * Download attachment (returns Cloudinary URL)
     */
    public byte[] download(Long id) {

        Attachment attach = attachRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Attachment not found"));

        return storage.read(attach.getStoragePath());
    }


}
