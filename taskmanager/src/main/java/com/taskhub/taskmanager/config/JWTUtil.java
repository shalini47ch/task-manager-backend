package com.taskhub.taskmanager.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JWTUtil {
    //load secret key from application.properties
    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, String role) {

        //this is the entire logic for generating jwt token
        return Jwts.builder().
                setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();

    }

    public String extractUser(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build().
                parseClaimsJws(token)
                .getBody()
                .getSubject();

    }

    //similarly do for extractRole
    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    //validate the token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUser(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //here also create a helper function to check for isTokenExpired
    //this will check whether the given token has expired or not
    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build().parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }


}
