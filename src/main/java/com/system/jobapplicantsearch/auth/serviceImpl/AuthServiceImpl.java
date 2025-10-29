package com.system.jobapplicantsearch.auth.serviceImpl;

import com.system.jobapplicantsearch.auth.dto.AuthResponse;
import com.system.jobapplicantsearch.auth.dto.LoginRequest;
import com.system.jobapplicantsearch.auth.dto.RegisterRequest;
import com.system.jobapplicantsearch.auth.entity.User;
import com.system.jobapplicantsearch.auth.enums.Role;
import com.system.jobapplicantsearch.auth.repository.UserRepository;
import com.system.jobapplicantsearch.auth.service.AuthService;
import com.system.jobapplicantsearch.auth.service.JWTService;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
  //  @Autowired
    private final UserRepository userRepository;
    private final JWTService jwtService;

  public AuthServiceImpl(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public String registerRequest(RegisterRequest registerRequest) throws Exception {
     User emailExist = userRepository.findByEmail(registerRequest.getEmail());
    if(emailExist!=null){
        throw new Exception("User already exist please login");
    }
        User user = User.builder().email(registerRequest.getEmail()).firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName()).role(registerRequest.getRole()).password(registerRequest.getPassword()).build();
       userRepository.save(user);
       return "Registered User";
    }

    @Override
    public AuthResponse loginRequest(LoginRequest loginRequest) throws IOException {
       User userFound = userRepository.findByEmail(loginRequest.getEmail());
       if(userFound!=null) {
           Long uid = userFound.
                   getId();
           Role role = userFound.getRole();
           return getJwt(uid, loginRequest.getEmail(), role );
       }
        throw new IOException("User does not exist");
    }

    private AuthResponse getJwt(Long uid, String email, Role role) {

       Instant now = Instant.now();

       Date expiredAtAccessToken = Date.from(now.plus(Duration.ofMinutes(2)));
       Date expiredAtRefreshToken = Date.from(now.plus(Duration.ofMinutes(3)));
       String accessToken =  jwtService.generateToken(String.valueOf(uid), expiredAtAccessToken);
       String refreshToken = jwtService.generateToken(String.valueOf(uid), expiredAtRefreshToken);
    return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).role(String.valueOf(role)).email(email).build();
    }
    }
