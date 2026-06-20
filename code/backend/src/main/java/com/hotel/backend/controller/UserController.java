package com.hotel.backend.controller;
import com.hotel.backend.dto.response.UserResponse;
import java.util.List;
import java.util.Map;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import java.util.LinkedHashMap;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller")
public class UserController {
    @Operation(summary = "List users", description = "Returns a list of all users")
    @GetMapping("/list-users")
    public List<UserResponse> listUsers(@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        // Implementation for listing users
        UserResponse user1 = new UserResponse();
        user1.setId(1L);
        user1.setFull_name("John Doe");
        user1.setEmail("");
        user1.setPhone("");
        user1.setAddress("");
        user1.setRole("CUSTOMER");
        user1.setStatus("ACTIVE");
        UserResponse user2 = new UserResponse();
        user2.setId(2L);
        user2.setFull_name("Jane Smith");
        user2.setEmail("");
        user2.setPhone("");
        user2.setAddress("");
        user2.setRole("ADMIN");
        user2.setStatus("ACTIVE");
        List<UserResponse> users = List.of(user1, user2);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "Users listed successfully");
        result.put("data", users);
        return users;
    }
}
