package com.task.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.ENUM.Role;
import com.task.Entity.UserProfileUpdate;
import com.task.Repository.UserProfileUpdateRepository;
import com.task.Security.RolePermissionConfig;

@Service
public class UserProfileUpdateService {

    @Autowired
    private UserProfileUpdateRepository userProfileRepo;

//    @Autowired
//    private RolePermissionConfig permission;

    public String updateUserProfile(UserProfileUpdate profile, String userOfficialEmail) {

        UserProfileUpdate user = userProfileRepo
                .findByUserOfficialEmail(userOfficialEmail)
                .orElse(new UserProfileUpdate());

        user.setUserOfficialEmail(userOfficialEmail);
        user.setUserName(profile.getUserName());
        user.setOrganizationNmae(profile.getOrganizationNmae());
        user.setDepartment(profile.getDepartment());
        user.setDesignation(profile.getDesignation());
        user.setActive(true);

        userProfileRepo.save(user);

        return "User Profile Saved Successfully";
    }
    
    public List<UserProfileUpdate> getAllProfile() {
        return userProfileRepo.findAll();
    }

    public UserProfileUpdate getProfileByEmail(String userOfficialEmail) {

        return userProfileRepo
                .findByUserOfficialEmail(userOfficialEmail)
                .orElseThrow(() -> new RuntimeException("User not Found"));
    }

    public UserProfileUpdate updateRole(String userOfficialEmail, Role newRole) {

        UserProfileUpdate user = userProfileRepo
                .findByUserOfficialEmail(userOfficialEmail)
                .orElseThrow(() -> new RuntimeException("User not Found"));

        user.setRole(newRole);

        return userProfileRepo.save(user);
    }
}	
	// Delete User 
//	public void deleteUser(String userOfficialEmail,Claims claim) {
//		 if(!permission.getRoleBasedPermissions(claim,Permission.USER_MANAGE)) {
//			 
//		 }
//	}


