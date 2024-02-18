package dev.awn.authenticationservice.core.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotNull(message = "First Name cannot be null")
    @NotBlank(message = "First Name cannot be empty")
    @Size(min = 3, max = 20, message = "First Name '${validatedValue}' must be between {min} and {max} characters long" )
    private String firstName;

    @NotNull(message = "Last Name cannot be null")
    @NotBlank(message = "Last Name cannot be empty")
    @Size(min = 3, max = 20, message = "Last Name '${validatedValue}' must be between {min} and {max} characters long")
    private String lastName;

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be empty")
    private String Username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 30, message = "Password must be between {min} and {max} characters long")
    private String password;
}
