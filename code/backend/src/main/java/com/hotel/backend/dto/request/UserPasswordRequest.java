package com.hotel.backend.dto.request;

import java.io.Serializable;

import lombok.Getter;
@Getter
public class UserPasswordRequest implements Serializable{
    private Long id;
    private String password;
    private String confirmPassword;
}
