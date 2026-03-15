package com.task.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.task.DTO.AuthResponseDTO;
import com.task.DTO.ForgotPasswordDTO;
import com.task.DTO.LoggedRequestDTO;
import com.task.DTO.LoginRequestDTO;
import com.task.DTO.RegisterRequestDTO;
import com.task.DTO.ResetPasswordDTO;
import com.task.Service.UserAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/Authentication")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService userService;

    // ---------------- REGISTER ----------------

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO register) {

        return ResponseEntity.ok(userService.register(register));
    }

    // ---------------- LOGIN ----------------

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO login) {

        return ResponseEntity.ok(userService.login(login));
    }

    // ---------------- FORGOT PASSWORD ----------------

    @PostMapping("/forgot_password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDTO forgotpassword) {

        userService.forgotPassword(forgotpassword.getUserOfficialEmail());

        return ResponseEntity.ok("Reset Password Email Sent on your Email");
    }

    // ---------------- RESET PASSWORD ----------------

    @PostMapping("/reset_password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO resetpassword) {

        userService.resetPassword(
                resetpassword.getToken(),
                resetpassword.getNewPassword());

        return ResponseEntity.ok("Password Reset Successfully");
    }

    // ---------------- LOGOUT ----------------

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        LoggedRequestDTO request = new LoggedRequestDTO();
        request.setToken(token);

        userService.loggedout(request);

        return ResponseEntity.ok("Logged out Successfully");
    }

    // ---------------- TEST ----------------

    @GetMapping("/test")
    public String test() {
        return "JWT working";
    }
}