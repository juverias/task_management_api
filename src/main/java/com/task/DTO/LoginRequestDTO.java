package com.task.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequestDTO {

    @NotBlank(message = "Email is required")
    @Email
    @Schema(example = "employee@company.com", description = "Official email address")
    private String userOfficialEmail;

    @NotBlank(message = "Password is required")
    @Schema(example = "Password@123", description = "User password")
    private String password;

}