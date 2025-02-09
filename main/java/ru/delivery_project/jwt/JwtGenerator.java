package ru.delivery_project.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtGenerator {
    private static final String SECRET_STRING = "delivery_project_secret_key_megasuperultrasecurity";
    private static final SecretKey SECRET = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(String email) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 3600000)) // 1 час
                .signWith(SECRET, SignatureAlgorithm.HS256)
                .compact();
    }

    public static SecretKey getSecretKey() {
        return SECRET;
    }
}
