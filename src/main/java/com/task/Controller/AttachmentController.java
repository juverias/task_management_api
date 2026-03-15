package com.task.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.task.Entity.Attachment;
import com.task.Service.AttachmentService;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    /**
     * Upload Attachment API
     */
    @PostMapping(value="/{issueId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Attachment> upload(@PathVariable Long issueId,
                                             @RequestParam("file") MultipartFile file,
                                             @RequestHeader(value="X-User-Email", required=false) String userOfficialEmail) {

        String author = (userOfficialEmail == null || userOfficialEmail.isEmpty())
                ? "system@gmail"
                : userOfficialEmail;

        Attachment attach = attachmentService.upload(issueId, file, author);

        return ResponseEntity.ok(attach);
    }


    /**
     * Download Attachment API
     */
    @GetMapping("/{attachmentId}/download")
    public ResponseEntity<byte[]> download(@PathVariable Long attachmentId){

        byte[] fileData = attachmentService.download(attachmentId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileData);
    }


}
