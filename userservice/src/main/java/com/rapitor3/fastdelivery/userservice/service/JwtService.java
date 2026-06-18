package com.rapitor3.fastdelivery.userservice.service;

import com.rapitor3.fastdelivery.userservice.model.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET = "super-secret-key-super-secret-key-123456";
    private static final long EXPIRATION_MINUTES = 60;

    Instant now = Instant.now();

    public String generateToken(AppUser user) {

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(EXPIRATION_MINUTES * 60)))
                .signWith(getSignedKey())
                .compact();
    }

    public SecretKey getSignedKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }
}
