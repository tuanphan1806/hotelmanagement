package com.hotel.backend.dto.request;
import lombok.Data;


@Data
public class ReservationRoomTypeRequest {
    private Long roomTypeId;
    private Integer quantity;
}