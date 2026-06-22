package com.hotel.backend.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;


@Builder
@Data
public class RoomTypeResponse {
    private Long id;
    private String typeName;
    private String description;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String imageUrl;
    private List<FacilityResponse.Summary> facilities;

}
