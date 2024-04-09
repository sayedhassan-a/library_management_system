package com.example.library.service;

import com.example.library.dto.AuthRequest;
import com.example.library.dto.AuthResponse;
import com.example.library.entity.User;

public interface AuthService {


    public AuthResponse register(User user  );
    public AuthResponse authenticate(AuthRequest request);
}
