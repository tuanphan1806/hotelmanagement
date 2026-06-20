package com.hotel.backend.dto.request;
import lombok.Data;

@Data
public class FacilityRequest {
    private String facilityName;
    private String description;
    private String icon;
}
