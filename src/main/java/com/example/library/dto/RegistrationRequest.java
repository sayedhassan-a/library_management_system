package com.example.library.dto;

import com.example.library.entity.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    @Size(min = 2, max = 36, message = "Username must be between 8 and 36 " +
            "characters")
    @NotNull
    private String username;

    @Size(min = 8, max = 32, message = "Password must be between 8 and 32 " +
            "characters")
    @Pattern(regexp = ".*[a-z].*", message = "Password must contain a lower " +
            "case character")
    @Pattern(regexp = ".*\\d.*", message = "Password must contain a digit")
    @Pattern(regexp = ".*[@$!%*?&].*", message = "Password must contain a " +
            "special character [@$!%*?&]")
    private String password;

}
