package com.hotel.backend.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
@Getter
public class UserPasswordRequest implements Serializable{
    @NotBlank(message = "id must be not null")
    private Long id;
    @NotBlank(message = "password must be not null")
    private String password;
    @NotBlank(message = "confirm password must be not null")
    private String confirmPassword;
}
