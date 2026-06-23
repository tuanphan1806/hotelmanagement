package com.hotel.backend.dto.response;

import com.hotel.backend.constant.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
 

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String phone;
    private String address;
    private Role role;
    private UserStatus status;
}

