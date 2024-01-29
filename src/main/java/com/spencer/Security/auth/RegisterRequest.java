package com.spencer.Security.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
     @NotEmpty(message = "Firstname should not be empty")
     private String firstname;

     @NotEmpty(message = "Lastname should not be empty")
     private String lastname;

     @NotEmpty(message = "Email should not be empty")
     @Email(message = "Email is not valid")
     private String email;

     @NotEmpty(message = "Password should not be empty")
     private String password;
}
