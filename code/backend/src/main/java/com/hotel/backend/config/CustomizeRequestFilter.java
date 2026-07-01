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
import com.hotel.backend.entity.InvalidatedToken;
import com.hotel.backend.repository.InvalidatedTokenRepository;
import com.hotel.backend.repository.UserRepository;
import com.hotel.backend.repository.UserTokenRepository;
import com.hotel.backend.service.JwtService;
import com.hotel.backend.service.UserServiceDetail;

import io.jsonwebtoken.ExpiredJwtException;

@Component
@Slf4j(topic = "CUSTOMIZE-REQUEST-FILTER")
@RequiredArgsConstructor
public class CustomizeRequestFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserServiceDetail userServiceDetail;
    private final InvalidatedTokenRepository invalidatedTokenRepository;
    private final UserRepository userRepository;
private final UserTokenRepository userTokenRepository;
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
            } catch (ExpiredJwtException e) {
                // Access token hết hạn → báo FE dùng refresh token
                log.warn("Access token expired for request: {}", request.getRequestURI());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"status\": 401, \"message\": \"Access token expired\"}");
                return;
            } catch (AccessDeniedException e) {
                log.error("Access denied: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"status\": 401, \"message\": \"Invalid token\"}");
                return;
            } catch (Exception e) {
                log.error("Token error: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"status\": 401, \"message\": \"Token error\"}");
                return;
            }

            String jti = jwtService.extractJti(authHeader, TokenType.ACCESS_TOKEN);

            InvalidatedToken invalidated = invalidatedTokenRepository.findByToken(jti).orElse(null);
            if (invalidated != null) {
                boolean isSessionReplaced = "SESSION_REPLACED".equals(invalidated.getReason());
            
                String message = isSessionReplaced
                        ? "{\"status\": 401, \"message\": \"Tài khoản đã đăng nhập ở nơi khác\"}"
                        : "{\"status\": 401, \"message\": \"Token đã bị vô hiệu hóa\"}";
            
                log.warn("Token blocked - reason={}, username={}", invalidated.getReason(), username);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(message);
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

        // Auth endpoints, except logout needs JWT authentication.
        if (uri.startsWith("/auth/") && !uri.equals("/auth/logout")) return true;

        // Public GET endpoints
        if (method.equals("GET")) {
            return uri.startsWith("/api/room-types") ||
                   uri.startsWith("/api/facilities") ||
                   uri.startsWith("/api/galleries") ||
                   uri.startsWith("/api/reviews") ||
                   uri.equals("/api/rooms/available") ||
                   uri.equals("/api/reservations/availability");
        }

        return false;
    }

}
