package com.spencer.Security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    private static final String SECRET_KEY = "Pk2bsYT+kyblfPX3XBt8BR38iYNb2p4gu0T1yTWxdA1TahzFXplsiHY1ydr6BrP2c3lynN3g43gMesYA8jTKPyw0wzmx613LSuyDUwW8nykIYOJnwrA8UK9mXkCxC6e4K0a3SUiNBeyc456nMOx7FjLbMeLYxmn7hJmwfMAlYpO/qV/1We6wIGR+zfEi+PVW1N+eEQ7x6+9cMnQ+h2qRrRAyQBN8L6WcruK6Yt5axDTu2ay0ikT4fcZXH/co+07oleRkMb3Yho5UKM+PjELg7u+bDTp2CwikmMwKpdCvofagyCnFtWFGXMtVyztebgF2YGFg84G8KLjuoRfdvaLNwBadNc4UODHsD0zmNHT9J24=";
    SecretKey key = Jwts.SIG.HS512.key().build();


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return  extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
