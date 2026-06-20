package com.hotel.backend.dto.response;

import com.hotel.backend.constant.AssignStatus;
import lombok.Data;


@Data
public class ReservationRoomResponse {
    private Long id;
    private Long reservationRoomTypeId;
    private Long roomId;
    private String roomName;
    private AssignStatus status;
}

