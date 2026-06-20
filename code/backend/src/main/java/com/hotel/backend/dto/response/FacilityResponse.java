package com.hotel.backend.dto.response;
import lombok.Data;

@Data
public class FacilityResponse {
    private Long id;
    private String facilityName;
    private String description;
    private String icon;
}

