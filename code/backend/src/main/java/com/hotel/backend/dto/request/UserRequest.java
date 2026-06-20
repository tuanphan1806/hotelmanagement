package com.hotel.backend.dto.request;

import com.hotel.backend.constant.*;
import lombok.Data;

@Data
public class UserRequest {
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Role role;
    private UserStatus status;
}
