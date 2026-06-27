package com.hotel.backend.config;

import java.io.IOException;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hotel.backend.constant.TokenType;
import com.hotel.backend.repository.InvalidatedTokenRepository;
import com.hotel.backend.service.JwtService;
import com.hotel.backend.service.UserServiceDetail;

@Component
@Slf4j(topic = "CUSTOMIZE-REQUEST-FILTER")
@RequiredArgsConstructor
public class CustomizeRequestFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserServiceDetail userServiceDetail;
    private final InvalidatedTokenRepository invalidatedTokenRepository;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        log.info("{} {}", request.getMethod(),request.getRequestURL());
        //TODO verify TOKEN
        String authHeader = request.getHeader("Authorization");
        if (authHeader!=null && authHeader.startsWith("Bearer ")) {
            authHeader=authHeader.substring(7);
            log.info("Bearer Header: {}",authHeader.substring(0,20));

            String username ="";
            try {
                username = jwtService.extractUsername(authHeader, TokenType.ACCESS_TOKEN);
                log.info("username: {}",username);
            } catch (AccessDeniedException e) {
                log.error("Access denied, message: {}",e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(e.getMessage());
                return;
            }

            String jti = jwtService.extractJti(authHeader, TokenType.ACCESS_TOKEN);
            if (invalidatedTokenRepository.existsByToken(jti)) {
                log.error("Token has been invalidated");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"message\": \"Token has been invalidated\"}");
                return;
            }

            UserDetails userDetails = userServiceDetail.loadUserByUsername(username);
            SecurityContext securityContext= SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
            filterChain.doFilter(request, response);
            return;
        }
        
        filterChain.doFilter(request, response);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        // Auth endpoints
        if (uri.startsWith("/auth/")) return true;

        // Public GET endpoints
        if (method.equals("GET")) {
            return uri.startsWith("/api/room-types") ||
                   uri.startsWith("/api/facilities") ||
                   uri.startsWith("/api/galleries") ||
                   uri.startsWith("/api/reviews") ||
                   uri.equals("/api/rooms/available");
        }
        if (method.equals("POST")) {
            return uri.startsWith("/api/reservations");
        }

        return false;
    }

}