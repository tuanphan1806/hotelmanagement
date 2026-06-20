package com.hotel.backend.dto.request;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ReservationRequest {
    private Long customerId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private String note;
    private Integer guestCount;
    private List<ReservationRoomTypeRequest> roomTypes; // Danh sách các loại phòng và số lượng chọn đặt
}