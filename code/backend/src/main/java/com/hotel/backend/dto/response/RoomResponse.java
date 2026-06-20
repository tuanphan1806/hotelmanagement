package com.hotel.backend.dto.response;

import com.hotel.backend.constant.*;
import lombok.Data;

@Data
public class RoomResponse {
    private Long id;
    private String roomName;
    private Long roomTypeId;
    private String roomTypeName;
    private Integer floor;
    private RoomStatus status;
    private CleaningStatus cleaningStatus;
    private String description;
}
