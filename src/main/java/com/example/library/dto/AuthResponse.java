package com.example.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@NotNull
@AllArgsConstructor
public class AuthResponse {
    String token;
}
