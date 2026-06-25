package com.hotel.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.backend.dto.request.SignInRequest;
import com.hotel.backend.dto.response.TokenResponse;
import com.hotel.backend.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION-CONTROLLER")
@Tag(name = "Authentication Controller")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Access token" ,description="Get accessToken and refreshToken")
    @PostMapping("/access-token")
    public TokenResponse getAccessToken(@RequestBody SignInRequest request) {
        log.info("Access token request");
        
        // return TokenResponse.builder().accessToken("dummy-accessToken").refreshToken("dummy-refreshToken").build();
        return authenticationService.getAccessToken(request);
    }
    

    @Operation(summary = "Refresh token" ,description="Get new accessToken and refreshToken")
    @PostMapping("/refresh-token")
    public TokenResponse getRefreshToken(@RequestHeader("Authorization") String refreshToken) {
        log.info("Refresh token request");
        
        // return TokenResponse.builder().accessToken("dummy-new-accessToken").refreshToken("dummy-refreshToken").build();
        return authenticationService.getRefreshToken(refreshToken);
    }
}
