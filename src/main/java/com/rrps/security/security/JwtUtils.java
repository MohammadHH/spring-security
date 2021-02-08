package com.rrps.security.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtils {
    private final String SECRET_KEY = "secret";

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String generateToken(UserDetails userDetails) {
        return createToken(userDetails);
    }

    private String createToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .compact();
    }

    //redundant
//    public boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractEmail(token);
//        return userDetails.getUsername().equals(username) && !isTokenExpired(token);
//    }
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
}
