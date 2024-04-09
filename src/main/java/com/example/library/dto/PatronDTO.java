package com.example.library.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatronDTO {

    @Size(min = 2, max = 36)
    @NotNull
    private String firstName;

    @Size(min = 2, max = 36)
    @NotNull
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @Size(min = 2, max = 50)
    @NotNull
    private String address;
}
