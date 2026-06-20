package com.hotel.backend.dto.response;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.hotel.backend.constant.ReservationStatus;


@Data
public class ReservationResponse {
    private Long id;
    private String reservationCode;
    private Long customerId;
    private String customerName;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private BigDecimal totalAmount;
    private ReservationStatus status;
    private Integer guestCount;
}
