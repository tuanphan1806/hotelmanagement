package com.hotel.backend.dto.response;

import com.hotel.backend.constant.CleaningStatus;
import com.hotel.backend.constant.RoomStatus;
import com.hotel.backend.entity.Room;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RoomResponse {

    private Long id;
    private String roomName;
    private Integer floor;
    private RoomStatus status;
    private CleaningStatus cleaningStatus;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // RoomType info (flatten, không nest cả object)
    private Long roomTypeId;
    private String roomTypeName;
    private BigDecimal price;

    // Static mapper
    public static RoomResponse from(Room room) {
        RoomResponseBuilder builder = RoomResponse.builder()
                .id(room.getId())
                .roomName(room.getRoomName())
                .floor(room.getFloor())
                .status(room.getStatus())
                .cleaningStatus(room.getCleaningStatus())
                .description(room.getDescription())
                .createdAt(room.getCreatedAt())
                .updatedAt(room.getUpdatedAt());

        if (room.getRoomType() != null) {
            builder.roomTypeId(room.getRoomType().getId())
                   .roomTypeName(room.getRoomType().getTypeName())
                   .price(room.getRoomType().getPrice());
        }

        return builder.build();
    }
}