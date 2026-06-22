package com.hotel.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Payload trả về cho client sau mọi thao tác với Facility.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityResponse {

    private Long id;
    private String facilityName;
    private String type;
    private String description;
    private String icon;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    /** Dạng rút gọn — nhúng trong RoomTypeResponse.facilities. */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Summary {
        private Long id;
        private String facilityName;
        private String type;
        private String icon;
    }
}