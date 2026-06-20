package com.hotel.backend.dto.response;

import lombok.Setter;
import lombok.Getter;
import java.io.Serializable;

@Getter
@Setter
public class UserResponse implements Serializable {
    private Long id;
    private String full_name;
    private String email;
    private String phone;
    private String address;
    private String role;
    private String status;
}