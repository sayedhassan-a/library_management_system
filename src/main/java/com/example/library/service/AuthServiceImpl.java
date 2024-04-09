package com.example.library.service;

import com.example.library.dto.AuthRequest;
import com.example.library.dto.AuthResponse;
import com.example.library.entity.User;
import com.example.library.repository.UserRepository;
import com.example.library.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(User user) {
        var savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(savedUser);
        return new AuthResponse(jwtToken);
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername());
        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken);
    }
}
