package com.system.jobapplicantsearch.auth.service;


import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.function.Function;

public interface JWTService {
    public String generateToken(String subject, Date expiredAt);
    public boolean validateToken(String token);
    public String extractUserName(String token);
    public Date extractExpiration(String token);
    public boolean isTokenExpired(String token);
    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction);
}
