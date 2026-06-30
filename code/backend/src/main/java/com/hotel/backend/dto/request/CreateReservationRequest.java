package com.hotel.backend.dto.request;
 
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateReservationRequest {
 
    @NotNull(message = "Ngày check-in không được để trống")
    @FutureOrPresent(message = "Ngày check-in không được ở quá khứ")
    private LocalDateTime checkIn;
 
    @NotNull(message = "Ngày check-out không được để trống")
    @Future(message = "Ngày check-out phải ở tương lai")
    private LocalDateTime checkOut;
 
    @Min(value = 1, message = "Số khách phải ít nhất 1 người")
    private Integer guestCount;
 
    private String note;
 
    @NotEmpty(message = "Phải chọn ít nhất 1 loại phòng")
    @Valid
    private List<RoomTypeItemRequest> roomTypes;
}