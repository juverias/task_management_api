package com.task.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task.Entity.UserAuth;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth,Long> {
             
	Optional<UserAuth>findByUserOfficialEmail(String userOfficialEmail); // Optional keyword is a Java 8 feature, used to avoid Null Pointer Excpetion 
    
	// Rest Password 
	Optional<UserAuth>findByResetToken(String resetToken);
}
