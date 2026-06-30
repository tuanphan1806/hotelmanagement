package com.hotel.backend.dto.request;
 
import jakarta.validation.constraints.NotNull;
import lombok.*;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignRoomRequest {
 
    @NotNull(message = "reservationRoomId không được để trống")
    private Long reservationRoomId;
 
    @NotNull(message = "roomId không được để trống")
    private Long roomId;
}