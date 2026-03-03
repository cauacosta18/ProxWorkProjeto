package com.br.proxwork.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.proxwork.Dtos.LoginDto;
import com.br.proxwork.Dtos.MeDto;
import com.br.proxwork.Dtos.RefreshRequest;
import com.br.proxwork.Services.AuthService;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto request) {
        return authService.login(request);

    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
        
        return authService.refresh(request);
    }

    
    @GetMapping("/me")
    public ResponseEntity<MeDto> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        return authService.getCurrentUser(authHeader);
    }

}

