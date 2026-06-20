package com.hotel.backend.dto.request;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class RoomTypeRequest {
    private String typeName;
    private String description;
    private BigDecimal price;
}