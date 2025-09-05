package com.kenti.antezana.sistema_de_gestion_de_reservas.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Value("${jwt.secret-key}")
    private String secret;
    @Value("${jwt.access-token.expiration}")
    private Long accessTokenExpiration;


    public String buildToken(UserDetails userDetails, Map<String, Object> claims, Long expiration) {
        return Jwts.builder()
                .signWith(getKey())
                .claims(claims)
                .claim("roles", userDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(r -> r.startsWith("ROLE_") ? r.substring(5) : r) // quita ROLE_
                        .collect(Collectors.toList()))
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    public String getAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("purpose", "access-token");
        return buildToken(userDetails, claims, accessTokenExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        return claimResolver.apply(extractAllClaims(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isValid(UserDetails user, String token){
        return !isExpired(token) && user.getUsername().equals(extractUsername(token));
    }


    private Key getKey(){
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}