package com.hotel.backend.dto.response;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Builder
public class TokenResponse implements Serializable{
    private String accessToken;
    private String refreshToken;
}
