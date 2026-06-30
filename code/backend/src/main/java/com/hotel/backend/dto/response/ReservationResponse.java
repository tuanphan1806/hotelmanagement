package com.hotel.backend.dto.response;

import com.hotel.backend.constant.ReservationStatus;
import com.hotel.backend.entity.Reservation;
import lombok.*;
 
import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.List;
 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
 
    private Long id;
    private String reservationCode;
    private Long customerId;
    private String customerName;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private ReservationStatus status;
    private String note;
    private String cancellationReason;
    private Integer guestCount;
    private LocalDateTime createdAt;
    private List<ReservationRoomTypeResponse> roomTypes;
 
    public static ReservationResponse from(Reservation r) {
        return ReservationResponse.builder()
                .id(r.getId())
                .reservationCode(r.getReservationCode())
                .customerId(r.getCustomer().getId())
                .customerName(r.getCustomer().getFullName())
                .checkIn(r.getCheckIn())
                .checkOut(r.getCheckOut())
                .totalAmount(r.getTotalAmount())
                .discountAmount(r.getDiscountAmount())
                .taxAmount(r.getTaxAmount())
                .status(r.getStatus())
                .note(r.getNote())
                .cancellationReason(r.getCancellationReason())
                .guestCount(r.getGuestCount())
                .createdAt(r.getCreatedAt())
                .build();
    }
 
    public static ReservationResponse fromWithDetails(Reservation r,
                                                      List<ReservationRoomTypeResponse> roomTypes) {
        ReservationResponse res = from(r);
        res.setRoomTypes(roomTypes);
        return res;
    }
}