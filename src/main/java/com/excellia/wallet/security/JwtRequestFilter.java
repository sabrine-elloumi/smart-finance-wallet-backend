package com.excellia.wallet.security;

import com.excellia.wallet.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserService userService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        
        logger.info("=== JwtRequestFilter - Processing request: {} {}", request.getMethod(), request.getRequestURI());
        
        final String authorizationHeader = request.getHeader("Authorization");
        
        String phoneNumber = null;
        String jwt = null;
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            logger.info("JWT token found in header (length: {})", jwt.length());
            try {
                phoneNumber = jwtUtil.getPhoneNumberFromToken(jwt);
                logger.info("Phone number extracted from token: {}", phoneNumber);
            } catch (Exception e) {
                logger.error("Error extracting phone number from token: {}", e.getMessage());
            }
        } else {
            logger.warn("No JWT token found in request headers - Header value: {}", authorizationHeader);
        }
        
        if (phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = userService.loadUserByUsername(phoneNumber);
                logger.info("User loaded: {} with authorities: {}", 
                    userDetails.getUsername(), userDetails.getAuthorities());
                
                if (jwtUtil.validateToken(jwt)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.info("✅ Authentication set in SecurityContext for user: {}", phoneNumber);
                } else {
                    logger.warn("❌ JWT token validation failed");
                }
            } catch (Exception e) {
                logger.error("Error setting authentication: {}", e.getMessage(), e);
            }
        }
        
        chain.doFilter(request, response);
    }
}