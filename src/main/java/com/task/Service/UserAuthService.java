package com.task.Service;

import java.util.Date;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.task.DTO.AuthResponseDTO;
import com.task.DTO.LoggedRequestDTO;
import com.task.DTO.LoginRequestDTO;
import com.task.DTO.RegisterRequestDTO;
import com.task.Entity.TokenBlockList;
import com.task.Entity.UserAuth;
import com.task.Repository.TokenBlockListRepository;
import com.task.Repository.UserAuthRepository;
import com.task.Security.JWTUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final UserAuthRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final EmailService emailService;
    private final TokenBlockListRepository tokenBlockRepo;

    // ---------------- REGISTER ----------------

    public AuthResponseDTO register(RegisterRequestDTO register) {

        if (userRepo.findByUserOfficialEmail(register.getUserOfficialEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        UserAuth user = new UserAuth();

        user.setUserName(register.getUserName());
        user.setUserOfficialEmail(register.getUserOfficialEmail());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setRole(register.getRole());

        userRepo.save(user);

        String token = jwtUtil.generateToken(user);

        return new AuthResponseDTO(token, "User registered successfully");
    }

    // ---------------- LOGIN ----------------

    public AuthResponseDTO login(LoginRequestDTO login) {

        UserAuth user = userRepo
                .findByUserOfficialEmail(login.getUserOfficialEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Credentials");
        }

        String token = jwtUtil.generateToken(user);

        return new AuthResponseDTO(token, "Login successful");
    }

    // ---------------- FORGOT PASSWORD ----------------

    public void forgotPassword(String userOfficialEmail) {

        UserAuth user = userRepo
                .findByUserOfficialEmail(userOfficialEmail)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        String token = UUID.randomUUID().toString();

        user.setResetToken(token);
        user.setResetTokenExpiry(new Date(System.currentTimeMillis() + 10 * 60 * 1000));

        userRepo.save(user);

        String resetToken =
                "http://localhost:8080/api/Authentication/reset_password?token=" + token;

        emailService.setResetEmail(user.getUserOfficialEmail(), resetToken);
    }

    // ---------------- RESET PASSWORD ----------------

    public void resetPassword(String token, String newPassword) {

        UserAuth user = userRepo
                .findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid Token"));

        if (user.getResetTokenExpiry().after(new Date())) {

            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null);
            user.setResetTokenExpiry(null);

            userRepo.save(user);
        }
    }

    // ---------------- LOGOUT ----------------

    public void loggedout(LoggedRequestDTO loggedOut) {

        Claims claims = jwtUtil.getClaims(loggedOut.getToken());

        TokenBlockList killToken = new TokenBlockList();

        killToken.setToken(loggedOut.getToken());
        killToken.setExpiry(claims.getExpiration());

        tokenBlockRepo.save(killToken);
    }
}