package com.hotel.backend.service.Impl;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hotel.backend.constant.TokenType;
import com.hotel.backend.constant.UserType;
import com.hotel.backend.dto.request.SignInRequest;
import com.hotel.backend.dto.response.TokenResponse;
import com.hotel.backend.entity.InvalidatedToken;
import com.hotel.backend.entity.UserToken;
import com.hotel.backend.exception.InvalidDataException;
import com.hotel.backend.repository.InvalidatedTokenRepository;
import com.hotel.backend.repository.UserRepository;
import com.hotel.backend.repository.UserTokenRepository;
import com.hotel.backend.service.AuthenticationService;
import com.hotel.backend.service.JwtService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION-SERVICE")
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final InvalidatedTokenRepository invalidatedTokenRepository;
    private final UserTokenRepository userTokenRepository;

    public TokenResponse getAccessToken(SignInRequest request){
        log.info("Get AccessToken");

        List<String> authorities= new ArrayList<>();

        // 1. Xác thực username/password qua AuthenticationManager
        // Nếu sai → throw AuthenticationException (BadCredentials, DisabledException, ...)
        try {
            Authentication authenticate= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            authenticate.getAuthorities().forEach(authority->authorities.add(authority.getAuthority()));

            SecurityContextHolder.getContext().setAuthentication(authenticate);            
        } catch (BadCredentialsException e) {
            throw e;
        } catch (AuthenticationException e) {
            log.info("Login failed, message: {}",e.getMessage());
            throw new InternalAuthenticationServiceException(e.getMessage());
        }

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Blacklist refresh token cũ nếu đang có session
        // Single-session chỉ áp dụng cho STAFF và ADMIN
        boolean isSingleSession = user.getType() == UserType.STAFF
                               || user.getType() == UserType.ADMIN;

        if (isSingleSession) {
            userTokenRepository.findById(user.getId()).ifPresent(existing -> {
                log.warn("User {} (type={}) logging in again — invalidating old session",
                        request.getUsername(), user.getType());

                //  Blacklist access token cũ
                if (existing.getAccessToken() != null) {
                    invalidatedTokenRepository.save(InvalidatedToken.builder()
                            .token(existing.getAccessToken())
                            .expiryTime(new Date())
                            .reason("SESSION_REPLACED")
                            .build());
                }

                invalidatedTokenRepository.save(InvalidatedToken.builder()
                        .token(existing.getRefreshToken())
                        .expiryTime(new Date())
                        .reason("SESSION_REPLACED")
                        .build());
                userTokenRepository.delete(existing);
            });
        }
        
        String accessToken=jwtService.generateAccessToken( request.getUsername(), authorities);
        String refreshToken=jwtService.generateRefreshToken( request.getUsername(), authorities);

         // Lưu JTI refresh token mới
        if (isSingleSession) {
            String accessJti  = jwtService.extractJti(stripBearer(accessToken), TokenType.ACCESS_TOKEN);
            String refreshJti = jwtService.extractJti(stripBearer(refreshToken), TokenType.REFRESH_TOKEN);
            userTokenRepository.save(UserToken.builder()
                    .userId(user.getId())
                    .accessToken(accessJti)
                    .refreshToken(refreshJti)
                    .createdAt(LocalDateTime.now())
                    .build());
            log.info("Single-session token saved for username={}, type={}", request.getUsername(), user.getType());
        }

        log.info("Login success for username={}", request.getUsername());
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    public TokenResponse getRefreshToken(String refreshToken) {
        log.info("Get RefreshToken");

        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            throw new InvalidDataException("Invalid refresh token format");
        }

        String token = refreshToken.substring(7).trim();
        // 1. Extract username từ refresh token
        String username = jwtService.extractUsername(token, TokenType.REFRESH_TOKEN);

        //  Check blacklist
        String rJti = jwtService.extractJti(token, TokenType.REFRESH_TOKEN);
        if (invalidatedTokenRepository.existsByToken(rJti)) {
            throw new InvalidDataException("Refresh token đã bị vô hiệu hóa, vui lòng đăng nhập lại");
        }

        // 2. Load user
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<String> authorities=new ArrayList<>();
        user.getAuthorities().forEach(authority->authorities.add(authority.getAuthority()));

        // Blacklist refresh token cũ
        invalidatedTokenRepository.save(InvalidatedToken.builder()
                .token(rJti)
                .expiryTime(new Date())
                .reason("LOGOUT")
                .build());

        // 3. Generate token mới
        String newAccessToken = jwtService.generateAccessToken( username, authorities);
        String newRefreshToken = jwtService.generateRefreshToken( username, authorities);

         // Cập nhật JTI mới
        String newRefreshJti = jwtService.extractJti(
        stripBearer(newRefreshToken), TokenType.REFRESH_TOKEN);
        userTokenRepository.save(UserToken.builder()
                .userId(user.getId())
                .refreshToken(newRefreshJti)
                .createdAt(LocalDateTime.now())
                .build());
        
        log.info("Refresh token rotated for username={}", username);
        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }



    @Override
    public void logout(String accessToken, String refreshToken) {
        log.info("Logout request");

        String token = accessToken.substring(7).trim();
        String rToken = refreshToken.substring(7).trim();
        // 1. Extract thông tin từ token
        String jti = jwtService.extractJti(token, TokenType.ACCESS_TOKEN);
        Date expiryTime = jwtService.extractExpiration(token, TokenType.ACCESS_TOKEN);
        
        if (invalidatedTokenRepository.existsByToken(jti)) {
            throw new InvalidDataException("Token đã bị vô hiệu hóa");
        }
        // 2. Lưu vào blacklist
        invalidatedTokenRepository.save(InvalidatedToken.builder()
                .token(jti)
                .expiryTime(expiryTime)
                .reason("LOGOUT")
                .build());

        // Blacklist refresh token
        String rJti        = jwtService.extractJti(rToken, TokenType.REFRESH_TOKEN);
        Date   rExpiryTime = jwtService.extractExpiration(rToken, TokenType.REFRESH_TOKEN);
        invalidatedTokenRepository.save(InvalidatedToken.builder()
                .token(rJti)
                .expiryTime(rExpiryTime)
                .reason("LOGOUT")
                .build());

        // Xóa record trong user_tokens
         String username = jwtService.extractUsername(token, TokenType.ACCESS_TOKEN);
        userRepository.findByUsername(username).ifPresent(user -> {
            boolean isSingleSession = user.getType() == UserType.STAFF
                                   || user.getType() == UserType.ADMIN;
            if (isSingleSession) {
                userTokenRepository.deleteById(user.getId());
                log.info("Single-session record removed for username={}", username);
            }
        });

        log.info("Logout success — tokens invalidated, jti={}", jti);
        }


        // ---- private helper ----
private String stripBearer(String token) {
    if (token == null) return null;
    return token.startsWith("Bearer ") ? token.substring(7).trim() : token.trim();
}
}
