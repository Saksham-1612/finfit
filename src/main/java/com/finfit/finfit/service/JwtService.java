package com.finfit.finfit.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final String secretKey;

    JwtService() throws NoSuchAlgorithmException {
//        KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA256");
//        SecretKey sk=keyGenerator.generateKey();
//        secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
        secretKey="N4sXh9qQ88S3t9JrF1bR3pP2r7D6bZ9g";
        getKey();
    }

    public String generateToken(String username) {
        Map<String,Object> claims = new HashMap<>();
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*30))
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String getUsernameFromToken(String token) {
       return extractClaims(token,Claims::getSubject);
    }

    private  <T> T extractClaims(String token, Function<Claims,T> claimsExtractor) {
        final Claims claims=extractAllClaims(token);
        return claimsExtractor.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpirationTime(token).before(new Date());
    }

    private Date extractExpirationTime(String token) {
        return extractClaims(token,Claims::getExpiration);
    }
}
