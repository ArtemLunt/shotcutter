package com.shotcutter.library.jwt;

import com.shotcutter.library.user.User;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Collections;
import java.util.Date;

public class JWTService {

    private String encryptionSecret;

    public JWTService(String encryptionSecret) {
        this.encryptionSecret = encryptionSecret;
    }

    public String getUserIdByToken(String accessToken) {
        return Jwts.parser()
                .setSigningKey(encryptionSecret)
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();
    }

    public String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getId());
        claims.put("scopes", Collections.singletonList("ROLE_USER"));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, encryptionSecret)
                .compact();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(encryptionSecret)
                .parseClaimsJws(token)
                .getBody();
    }

}
