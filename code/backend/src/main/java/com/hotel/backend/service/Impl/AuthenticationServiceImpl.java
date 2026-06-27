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
import com.hotel.backend.dto.request.SignInRequest;
import com.hotel.backend.dto.response.TokenResponse;
import com.hotel.backend.entity.InvalidatedToken;
import com.hotel.backend.exception.InvalidDataException;
import com.hotel.backend.repository.InvalidatedTokenRepository;
import com.hotel.backend.repository.UserRepository;
import com.hotel.backend.service.AuthenticationService;
import com.hotel.backend.service.JwtService;

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

        
        String accessToken=jwtService.generateAccessToken( request.getUsername(), authorities);
        String refreshToken=jwtService.generateRefreshToken( request.getUsername(), authorities);

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

        String token = refreshToken.substring(7);
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

        // 3. Generate token mới
        String newAccessToken = jwtService.generateAccessToken( username, authorities);
        String newRefreshToken = jwtService.generateRefreshToken( username, authorities);

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }


    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @Override
    public void logout(String accessToken, String refreshToken) {
        log.info("Logout request");

        String token = accessToken.substring(7).trim();

        // 1. Extract thông tin từ token
        String jti = jwtService.extractJti(token, TokenType.ACCESS_TOKEN);
        Date expiryTime = jwtService.extractExpiration(token, TokenType.ACCESS_TOKEN);
        
        if (invalidatedTokenRepository.existsByToken(jti)) {
            throw new InvalidDataException("Token đã bị vô hiệu hóa");
        }
        // 2. Lưu vào blacklist
        invalidatedTokenRepository.save(
            InvalidatedToken.builder()
                .token(jti)
                .expiryTime(expiryTime)
                .build()
        );

        String rToken = refreshToken.substring(7).trim();
        String rJti = jwtService.extractJti(rToken, TokenType.REFRESH_TOKEN);
        Date rExpiryTime = jwtService.extractExpiration(rToken, TokenType.REFRESH_TOKEN);

        invalidatedTokenRepository.save(InvalidatedToken.builder()
                .token(rJti)
                .expiryTime(rExpiryTime)
                .build());

        log.info("Access + Refresh token invalidated for jti: {}", jti);
        }
}
