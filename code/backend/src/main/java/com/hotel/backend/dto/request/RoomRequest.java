package com.hotel.backend.dto.request;
import com.hotel.backend.constant.*;
import lombok.Data;


@Data
public class RoomRequest {
    private String roomName;
    private Long roomTypeId;
    private Integer floor;
    private RoomStatus status;
    private CleaningStatus cleaningStatus;
    private String description;
}

