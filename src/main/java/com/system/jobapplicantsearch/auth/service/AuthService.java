package com.system.jobapplicantsearch.auth.service;

import com.system.jobapplicantsearch.auth.dto.AuthResponse;
import com.system.jobapplicantsearch.auth.dto.LoginRequest;
import com.system.jobapplicantsearch.auth.dto.RegisterRequest;

import java.io.IOException;

public interface AuthService {
    String registerRequest(RegisterRequest registerRequest) throws Exception;

    AuthResponse loginRequest(LoginRequest loginRequest) throws IOException;
}
