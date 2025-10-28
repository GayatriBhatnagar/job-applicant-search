package com.system.jobapplicantsearch.auth.controller;

import com.system.jobapplicantsearch.auth.dto.AuthResponse;
import com.system.jobapplicantsearch.auth.dto.LoginRequest;
import com.system.jobapplicantsearch.auth.dto.RegisterRequest;
import com.system.jobapplicantsearch.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("auth")
@Tag(name= "AuthApis" , description = "manage security")
public class AuthController {

    private  final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register User")
    @ApiResponse(responseCode = "201",description = "User Registered")
    @PostMapping("/register")
    public ResponseEntity<String> registerRequest(@RequestBody RegisterRequest registerRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerRequest(registerRequest));

    }

    @Operation(summary = "login Request")
    @ApiResponse(responseCode = "200", description = "user logged in")
    @PostMapping("/login")
    public AuthResponse loginRequest(@RequestBody LoginRequest loginRequest) throws IOException {
     return authService.loginRequest(loginRequest);
    }
}
