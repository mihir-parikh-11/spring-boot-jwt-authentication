package com.auth.app.security.service;

import com.auth.app.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    @Value("${app.jwt.expiration}")
    private long accessTokenExpirationMs;

    @Value("${app.jwt.refreshExpiration}")
    private long refreshTokenExpirationMs;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !(isTokenExpired(token));
    }

    public String generateAccessToken(User user) {
        String subject = user.getUsername();
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRoleNames());
        claims.put("user_id", user.getId());
        claims.put("full_name", user.getFullName());
        return createToken(claims, subject, accessTokenExpirationMs);
    }

    public String generateRefreshToken(User user) {
        String subject = user.getUsername();
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRoleNames());
        claims.put("user_id", user.getId());
        claims.put("full_name", user.getFullName());
        return createToken(claims, subject, refreshTokenExpirationMs);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    private String createToken(Map<String, ?> claims, String subject, long expirationMs) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * expirationMs))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
