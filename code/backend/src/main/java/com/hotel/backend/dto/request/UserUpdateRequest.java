package com.hotel.backend.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;
@Getter
@ToString
public class UserUpdateRequest implements Serializable{

    private Long id;

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
