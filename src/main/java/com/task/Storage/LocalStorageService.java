package com.task.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


@Service
public class LocalStorageService implements StorageService {

    // Cloudinary client used to delete files from cloud storage
    @Autowired
    private Cloudinary cloudinary;

    private final Path base = Paths.get("upload");     // Base directory for local uploads

    public LocalStorageService() {    // Create upload directory at application startup
        try {
            Files.createDirectories(base);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't initialize local storage");
        }
    }

    /**
     * Stores a file locally inside a given folder.
     *
     * @param file   uploaded multipart file
     * @param folder sub-folder inside upload directory
     * @return stored file path as String
     */
    
    @Override
    public String store(MultipartFile file, String folder) {
        try {
            // Create the folder if it doesn't exist
            Path dir = base.resolve(folder);
            Files.createDirectories(dir);

            // Generate a unique filename to avoid collisions
            String name = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Resolve final file path
            Path path = dir.resolve(name);

            // Copy file contents to disk
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            return path.toString();

        } catch (Exception e) {
            throw new RuntimeException("File upload failed", e);
        }
    }

    /**
     * Reads a locally stored file and returns its bytes.
     *
     * @param storagePath path of the stored file
     */
    
    @Override
    public byte[] read(String storagePath) {
        try {
            return Files.readAllBytes(Paths.get(storagePath));
        } catch (Exception e) {
            throw new RuntimeException("File read failed", e);
        }
    }

    /**
     * Deletes a file from Cloudinary using its public ID.
     *
     * @param publicId Cloudinary public identifier
     */
    
    @Override
    public void delete(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete from cloud", e);
        }
    }
}


