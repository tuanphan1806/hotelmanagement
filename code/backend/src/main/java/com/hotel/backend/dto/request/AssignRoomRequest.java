package com.hotel.backend.dto.request;

import com.hotel.backend.constant.AssignStatus;
import lombok.Data;

@Data
public class AssignRoomRequest {
    private Long reservationRoomTypeId;
    private Long roomId; // ID phòng cụ thể do lễ tân chọn gán
    private AssignStatus status;
}
