package com.hotel.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.backend.dto.request.SignInRequest;
import com.hotel.backend.dto.request.UserCreationRequest;
import com.hotel.backend.dto.response.TokenResponse;
import com.hotel.backend.service.AuthenticationService;
import com.hotel.backend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    @PostMapping("/login")
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

    @Operation(summary = "Logout", description = "Invalidate access token")
    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestHeader("Authorization") String accessToken, @RequestHeader("Refresh-Token") String refreshToken) {
        log.info("Logout request");
        authenticationService.logout(accessToken,refreshToken);
        Map<String,Object>result=new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","Logout successfully");
        result.put("data","");
        
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    private final UserService userService;

    @Operation(summary = "Create User", description = "API register new user to database")
    @PostMapping("/register")
    public ResponseEntity<Object> CreateUser(@RequestBody @Valid UserCreationRequest request){
        Map<String,Object>result=new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message","regist successfully");

        result.put("data",userService.save(request));
        
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @GetMapping("/confirm-email")
    public void confirmEmail(@RequestParam String secretCode ,HttpServletResponse response) throws IOException{
        log.info("Confirm Email: {}", secretCode);
        try {
            //TODO check or compare secretCode
            userService.verifyEmail(secretCode);

        } catch (Exception e) {
            log.error("Confirm Email failed: {}", e.getMessage());
        } finally{
            response.sendRedirect("https://www.facebook.com");
        }
    }
}
