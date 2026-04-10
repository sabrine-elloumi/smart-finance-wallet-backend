package com.excellia.wallet.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    // Correction : Suppression de la méthode dupliquée et ajout de l'initialisation différée
    private Key signingKey;
    
    private Key getSigningKey() {
        if (signingKey == null) {
            byte[] keyBytes = secret.getBytes();
            logger.info("JWT secret key length: {} bytes", keyBytes.length);
            if (keyBytes.length < 32) {
                logger.warn("⚠️ JWT secret key is too short: {} bytes. It should be at least 32 bytes for HS256.", keyBytes.length);
                logger.warn("Current secret: '{}' (length: {})", secret, secret.length());
            }
            signingKey = Keys.hmacShaKeyFor(keyBytes);
        }
        return signingKey;
    }
    
    public String generateToken(String phoneNumber, String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        
        logger.debug("Generating JWT token for user: {} (ID: {})", phoneNumber, userId);
        logger.debug("Token expiration: {} ms", expiration);
        
        return Jwts.builder()
            .setSubject(phoneNumber)
            .claim("userId", userId)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }
    
    public String getPhoneNumberFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            logger.error("Error extracting phone number from token: {}", e.getMessage());
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
    
    public String getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
            return claims.get("userId", String.class);
        } catch (Exception e) {
            logger.error("Error extracting user ID from token: {}", e.getMessage());
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            logger.debug("JWT token validated successfully");
            return true;
        } catch (Exception e) {
            logger.error("JWT token validation failed: {}", e.getMessage());
            return false;
        }
    }
    
    // Méthode utilitaire pour vérifier si le token est expiré
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            logger.error("Error checking token expiration: {}", e.getMessage());
            return true;
        }
    }
    
    // Méthode pour obtenir le temps restant avant expiration (en millisecondes)
    public long getTokenRemainingTime(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
            return claims.getExpiration().getTime() - System.currentTimeMillis();
        } catch (Exception e) {
            logger.error("Error getting token remaining time: {}", e.getMessage());
            return 0;
        }
    }
}