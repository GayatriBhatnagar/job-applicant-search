package com.system.jobapplicantsearch.auth.serviceImpl;

import com.system.jobapplicantsearch.auth.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private Key key;

    @PostConstruct
    public void init(){
       key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }


    @Override
    public String generateToken(String subject, Date expiredAt) {

        Instant instant = Instant.now();
        Date issuedAt = Date.from(instant);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    @Override
    public String extractUserName(String token) {
        return extractClaim(token , Claims::getId);
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public boolean isTokenExpired(String token) {
     return new Date().toInstant().isBefore(extractExpiration(token).toInstant());
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        Claims claim = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        return claimsTFunction.apply(claim);
    }
}
