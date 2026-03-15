package com.task.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.task.ENUM.Role;
import com.task.Entity.UserProfileUpdate;
import com.task.Service.UserProfileUpdateService;

@RestController
@RequestMapping("/api/profile_update")
public class UserProfileUpdateController {

    @Autowired
    private UserProfileUpdateService userProfileService;

    @PutMapping("/updateProfile/{userOfficialEmail}")
    public ResponseEntity<String> updateUserProfile(
            @RequestBody UserProfileUpdate profile,
            @PathVariable String userOfficialEmail) {

        return ResponseEntity.ok(
                userProfileService.updateUserProfile(profile, userOfficialEmail)
        );
    }

    @GetMapping("/{userOfficialEmail}")
    public ResponseEntity<UserProfileUpdate> getProfileByEmail(
            @PathVariable String userOfficialEmail) {

        return ResponseEntity.ok(
                userProfileService.getProfileByEmail(userOfficialEmail)
        );
    }

    @PutMapping("/updateNewRole")
    public ResponseEntity<UserProfileUpdate> updateNewRole(
            @RequestParam String userOfficialEmail,
            @RequestParam Role role) {

        return ResponseEntity.ok(
                userProfileService.updateRole(userOfficialEmail, role)
        );
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<UserProfileUpdate>> getAllUserProfile() {

        return ResponseEntity.ok(userProfileService.getAllProfile());
    }
}