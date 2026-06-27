package com.hotel.backend.service.Impl;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import com.hotel.backend.constant.TokenType;
import com.hotel.backend.exception.InvalidDataException;
import com.hotel.backend.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j(topic = "JWTSERVICE")
public class JwtServiceImpl implements JwtService{
    @Value("${jwt.expiryMinutes}")
    private long expiryMinutes;
    @Value("${jwt.expiryDay}")
    private long expiryDay;

    @Value("${jwt.accessKey}")
    private String accessKey;

    @Value("${jwt.refreshKey}")
    private String refreshKey;

    public String generateAccessToken(String username, List<String> authorities){
        log.info("Generate AccessToken for username {} with authorities {}", username,authorities);
        Map<String,Object> claims=new HashMap<>();

        claims.put("role", authorities);

        return generateToken(claims, username);
    }
    public String generateRefreshToken(String username, List<String> authorities){
        log.info("Generate RefreshToken for username {} with authorities {}", username,authorities);
        Map<String,Object> claims=new HashMap<>();

        claims.put("role", authorities);

        return generateRefershToken(claims, username);
    }
    public String extractUsername(String token, TokenType type){
        log.info("Extract username from token {} with type {}",token,type);
        return extractClaims(type,token,Claims::getSubject);
    }
    private <T> T extractClaims(TokenType type, String token , Function<Claims,T> claimsExtractor){
        final Claims claims= extractAllClaims(token,type);
        return claimsExtractor.apply(claims);
    }
    private Claims extractAllClaims(String token, TokenType type) {
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) getKey(type))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException | MalformedJwtException e) {
            throw new InvalidDataException("Access denied!, error " + e.getMessage());
        }
    }

    private String generateToken(Map<String, Object> claims, String username) {
        log.info("Generate AccessToken for user {} with claims {}", username, claims);
        return Jwts.builder()
                .id(UUID.randomUUID().toString()) 
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * expiryMinutes))
                .signWith(getKey(TokenType.ACCESS_TOKEN))      
                .compact();
    }
    private String generateRefershToken(Map<String, Object> claims, String username) {
        log.info("Generate RefershToken for user {} with claims {}", username, claims);
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 *60*24* expiryDay))
                .signWith(getKey(TokenType.REFRESH_TOKEN))      
                .compact();
    }

    private Key getKey(TokenType type){
        switch (type) {
            case ACCESS_TOKEN: 
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
        
            case REFRESH_TOKEN:
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
            default:
                throw new InvalidDataException("Invalid token type");
        }
    }


    public String extractJti(String token, TokenType type) {
        return extractClaims(type, token, Claims::getId);
    }

    public Date extractExpiration(String token, TokenType type) {
        return extractClaims(type, token, Claims::getExpiration);
    }
    
}
