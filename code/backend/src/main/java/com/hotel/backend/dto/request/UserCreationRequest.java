package com.hotel.backend.dto.request;


import lombok.Getter;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
public class UserCreationRequest implements Serializable{

    @NotBlank
    private String fullName;

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    // @NotBlank
    // private String password;

    @NotBlank
    private String phone;

    private String address;
}
